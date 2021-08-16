package com.gcs.gcsplatform.web.screens.trade.tradecontainer;

import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.gcs.gcsplatform.service.TradeService;
import com.gcs.gcsplatform.web.screens.pnl.PnlChartScreen;
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
import org.apache.commons.lang3.StringUtils;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

@UiDescriptor("trade-container-browse.xml")
@LookupComponent("tradeContainersTable")
@LoadDataBeforeShow
public abstract class TradeContainerBrowse<T extends TradeContainer> extends StandardLookup<T> {

    @Inject
    protected TradeService tradeService;
    @Inject
    protected Dialogs dialogs;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    protected CollectionLoader<T> tradeContainersDl;

    /**
     * Updates list of trades on each close because the edit screen might be closed with discard in case of trade close.
     */
    @Install(to = "tradeContainersTable.edit", subject = "afterCloseHandler")
    protected void tradeContainersTableEditAfterCloseHandler(
            @SuppressWarnings("unused") AfterCloseEvent afterCloseEvent) {
        tradeContainersDl.load();
    }

    @Install(to = "tradeContainersTable", subject = "styleProvider")
    protected String tradeContainersTableStyleProvider(T entity, String property) {
        Trade trade = entity.getTrade();
        if (property == null && (StringUtils.isEmpty(trade.getBuybroker()) || StringUtils.isEmpty(
                trade.getSellbroker()))) {
            return "v-table-row highlight-row";
        }
        return null;
    }

    @Subscribe("pnlChartBtn")
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption(messageBundle.getMessage("buildPnlChartDialog.caption"))
                .withParameter(InputParameter.dateParameter("startDate")
                        .withCaption(messageBundle.getMessage("startDate"))
                        .withDefaultValue(getFirstDayOfMonth()))
                .withParameter(InputParameter.dateParameter("endDate")
                        .withCaption(messageBundle.getMessage("endDate"))
                        .withDefaultValue(getLastDayOfMonth()))
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
                        Collection<T> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass(), startDate,
                                endDate);
                        showPnlChartScreen(trades);
                    }
                })
                .show();
    }

    protected void showPnlChartScreen(Collection<? extends TradeContainer> tradeContainers) {
        PnlChartScreen pnlChartScreen = screenBuilders.screen(this)
                .withScreenClass(PnlChartScreen.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .build();
        pnlChartScreen.setTradeContainers(tradeContainers);
        pnlChartScreen.show();
    }

    public abstract Class<T> getTradeClass();
}