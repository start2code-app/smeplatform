package com.gcs.gcsplatform.web.screens.trade;

import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.TradeService;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.gcs.gcsplatform.web.components.TradeValidationBean;
import com.gcs.gcsplatform.web.events.TradeClosedEvent;
import com.gcs.gcsplatform.web.screens.pnl.PnlChartScreen;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

@UiDescriptor("trade-browse.xml")
@LookupComponent("tradesTable")
@LoadDataBeforeShow
public abstract class TradeBrowse<T extends Trade> extends StandardLookup<T> {

    @Inject
    protected TradeService tradeService;
    @Inject
    protected Dialogs dialogs;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected TradeValidationBean tradeValidationBean;
    @Inject
    protected PnlCalculationBean pnlCalculationBean;

    @Inject
    protected CollectionLoader<T> tradesDl;

    @Install(to = "tradesTable", subject = "styleProvider")
    protected String tradesTableStyleProvider(T entity, String property) {
        if (property == null) {
            if (tradeValidationBean.hasBlankRequiredFields(entity)) {
                return "v-table-row pink-row";
            }

            if (tradeValidationBean.hasZeroPnl(entity)) {
                return "v-table-row red-row";
            }
        }
        return null;
    }

    @Subscribe("pnlChartBtn")
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Date today = new Date();
        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("buildPnlChartDialog.caption"))
                .withParameter(InputParameter.dateParameter("startDate")
                        .withCaption(messageBundle.getMessage("startDate"))
                        .withDefaultValue(getFirstDayOfMonth(today)))
                .withParameter(InputParameter.dateParameter("endDate")
                        .withCaption(messageBundle.getMessage("endDate"))
                        .withDefaultValue(getLastDayOfMonth(today)))
                .withValidator(validationContext -> {
                    Date startDate = validationContext.getValue("startDate");
                    Date endDate = validationContext.getValue("endDate");
                    ValidationErrors errors = new ValidationErrors();
                    if (startDate != null && endDate != null && (startDate.after(endDate) || endDate.before(
                            startDate))) {
                        errors.add(messageBundle.getMessage("dateInterval.validationMsg"));
                    }
                    return errors;
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        Date startDate = inputDialogCloseEvent.getValue("startDate");
                        Date endDate = inputDialogCloseEvent.getValue("endDate");
                        Collection<T> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass(),
                                ViewBuilder.of(getTradeClass())
                                        .addView(View.LOCAL)
                                        .build(),
                                startDate,
                                endDate);
                        recalculatePnl(trades);
                        showPnlChartScreen(trades);
                    }
                })
                .show();
    }

    /**
     * Recalculates PNL.
     * <p>
     * Note: recalculated PNL value is not being persisted, it is only used in chart.
     *
     * @param trades Trades
     */
    protected void recalculatePnl(Collection<T> trades) {
        for (T trade : trades) {
            pnlCalculationBean.updatePnl(trade);
        }
    }

    protected void showPnlChartScreen(Collection<T> trades) {
        PnlChartScreen pnlChartScreen = screenBuilders.screen(this)
                .withScreenClass(PnlChartScreen.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .build();
        pnlChartScreen.setTrades(trades);
        pnlChartScreen.show();
    }

    @EventListener
    protected void onTradeClose(TradeClosedEvent event) {
        tradesDl.load();
    }

    public abstract Class<T> getTradeClass();
}