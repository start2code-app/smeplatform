package com.gcs.gcsplatform.web.screens.trade.closedlivetrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedLiveTrade.browse")
@UiDescriptor("closed-live-trade-browse.xml")
public class ClosedLiveTradeBrowse extends TradeBrowse<ClosedLiveTrade> {

    @Inject
    protected Button pnlChartBtn;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        pnlChartBtn.setVisible(false);
    }

    @Override
    public Class<ClosedLiveTrade> getTradeClass() {
        return ClosedLiveTrade.class;
    }
}