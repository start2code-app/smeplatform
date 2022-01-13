package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.invoice.InvoiceSnapshotService;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.gcs.gcsplatform.web.screens.trade.btnpnlchartdialogfragment.BtnPnlChartDialogFragment;
import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;
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
    @Inject
    private Button cpySimplrBtn;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        cpySimplrBtn.setVisible(false);
        btnPnlChartDialogFragment.setTradeClass(ClosedTrade.class);
        btnPnlChartDialogFragment.setCaption(messageBundle.getMessage("closedTradesPnl.caption"));
    }

    @Install(to = "tradesTable.edit", subject = "enabledRule")
    protected boolean tradesTableEditEnabledRule() {
        ClosedTrade trade = tradesTable.getSingleSelected();
        Date invoiceDate = trade.getInvoiceDate();
        return invoiceDate == null
                || isDateInCurrentMonth(invoiceDate)
                || security.isSpecificPermitted("app.editClosedTradesWhenSnapshotTaken")
                || !invoiceSnapshotService.snapshotIsTaken(getFirstDayOfMonth(invoiceDate), getLastDayOfMonth(invoiceDate));
    }

    @Install(to = "tradesTable.create", subject = "enabledRule")
    protected boolean tradesTableCreateEnabledRule() {

        return false;
    }

}