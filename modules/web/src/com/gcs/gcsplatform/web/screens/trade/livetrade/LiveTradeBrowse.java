package com.gcs.gcsplatform.web.screens.trade.livetrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.browse")
@UiDescriptor("live-trade-browse.xml")
public class LiveTradeBrowse extends TradeBrowse<LiveTrade> {

    @Inject
    protected Button pnlChartBtn;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        pnlChartBtn.setVisible(false);
    }

    @Override
    public Class<LiveTrade> getTradeClass() {
        return LiveTrade.class;
    }
}