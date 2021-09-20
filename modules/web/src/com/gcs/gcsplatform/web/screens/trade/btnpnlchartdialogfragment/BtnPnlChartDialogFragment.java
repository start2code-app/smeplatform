package com.gcs.gcsplatform.web.screens.trade.btnpnlchartdialogfragment;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.trade.TradeService;
import com.gcs.gcsplatform.web.components.pnl.PnlChartBean;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

@UiController("gcsplatform_BtnPnlChartDialogFragment")
@UiDescriptor("btn-pnl-chart-dialog-fragment.xml")
public class BtnPnlChartDialogFragment extends ScreenFragment {

    protected String caption;
    protected Class<? extends Trade> tradeClass;

    @Inject
    protected TradeService tradeService;
    @Inject
    protected Dialogs dialogs;
    @Inject
    protected MessageBundle messageBundle;

    @Inject
    protected PnlChartBean pnlChartBean;

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setTradeClass(Class<? extends Trade> tradeClass) {
        this.tradeClass = tradeClass;
    }

    @Subscribe("pnlChartDialogBtn")
    protected void onPnlChartDialogBtnClick(Button.ClickEvent event) {
        Date today = getCurrentDate();
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
                        Collection<? extends Trade> trades = tradeService.getEnrichedTradesForPnlChart(tradeClass,
                                startDate, endDate, ViewBuilder.of(tradeClass)
                                        .addView(View.LOCAL)
                                        .build()
                        );
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String heading = String.format("%s-%s", dateFormat.format(startDate),
                                dateFormat.format(endDate));
                        pnlChartBean.showPnlChartScreen(this, trades, caption, heading);
                    }
                })
                .show();
    }
}