package com.gcs.gcsplatform.web.screens.trade.btnpnlchartdialogfragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.trade.TradeService;
import com.gcs.gcsplatform.web.components.pnl.PnlChartBean;
import com.gcs.gcsplatform.web.util.ScreenUtil;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.components.Button;
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
    protected MessageBundle messageBundle;
    @Inject
    protected Messages messages;
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
        ScreenUtil.showDateIntervalSelectionDialog(getHostScreen(),
                messageBundle.getMessage("buildPnlChartDialog.caption"),
                inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        Date startDate = inputDialogCloseEvent.getValue("startDate");
                        Date endDate = inputDialogCloseEvent.getValue("endDate");
                        showPnlChartScreen(startDate, endDate);
                    }
                }, getFirstDayOfMonth(today), getLastDayOfMonth(today));
    }

    protected void showPnlChartScreen(Date startDate, Date endDate) {
        Collection<? extends Trade> trades = tradeService.getEnrichedTradesForPnlChart(tradeClass,
                startDate, endDate, ViewBuilder.of(tradeClass)
                        .addView(View.LOCAL)
                        .build());
        DateFormat dateFormat = new SimpleDateFormat(messages.getMainMessage("dateFormat"));
        String heading = String.format("%s-%s", dateFormat.format(startDate),
                dateFormat.format(endDate));
        pnlChartBean.showPnlChartScreen(this, trades, caption, heading);
    }
}