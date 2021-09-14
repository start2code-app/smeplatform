package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.gcs.gcsplatform.web.screens.trade.btnpnlchartdialogfragment.BtnPnlChartDialogFragment;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedTrade.browse")
@UiDescriptor("closed-trade-browse.xml")
public class ClosedTradeBrowse extends TradeBrowse<ClosedTrade> {

    @Inject
    protected BtnPnlChartDialogFragment btnPnlChartDialogFragment;

    @Inject
    protected MessageBundle messageBundle;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        btnPnlChartDialogFragment.setTradeClass(ClosedTrade.class);
        btnPnlChartDialogFragment.setCaption(messageBundle.getMessage("closedTradesPnl.caption"));
    }
}