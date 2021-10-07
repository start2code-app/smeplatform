package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.invoice.InvoiceSnapshotService;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.gcs.gcsplatform.web.screens.trade.btnpnlchartdialogfragment.BtnPnlChartDialogFragment;
import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getPreviousMonth;
import static com.gcs.gcsplatform.util.DateUtils.isDateInCurrentMonth;

@UiController("gcsplatform_ClosedTrade.browse")
@UiDescriptor("closed-trade-browse.xml")
public class ClosedTradeBrowse extends TradeBrowse<ClosedTrade> {

    @Inject
    protected BtnPnlChartDialogFragment btnPnlChartDialogFragment;
    @Inject
    protected GroupTable<ClosedTrade> tradesTable;

    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Security security;
    @Inject
    protected InvoiceSnapshotService invoiceSnapshotService;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        btnPnlChartDialogFragment.setTradeClass(ClosedTrade.class);
        btnPnlChartDialogFragment.setCaption(messageBundle.getMessage("closedTradesPnl.caption"));
    }

    @Install(to = "tradesTable.edit", subject = "enabledRule")
    protected boolean tradesTableEditEnabledRule() {
        ClosedTrade trade = tradesTable.getSingleSelected();
        return isDateInCurrentMonth(trade.getInvoiceDate())
                || security.isSpecificPermitted("app.editClosedTradesWhenSnapshotTaken")
                || !invoiceSnapshotService.snapshotIsTaken(getPreviousMonth());
    }
}