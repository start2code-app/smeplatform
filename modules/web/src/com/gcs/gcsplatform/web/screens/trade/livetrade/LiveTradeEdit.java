package com.gcs.gcsplatform.web.screens.trade.livetrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerEdit;
import com.haulmont.cuba.gui.components.TabSheet;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.edit")
@UiDescriptor("live-trade-edit.xml")
public class LiveTradeEdit extends TradeContainerEdit<LiveTrade> {

    @Inject
    protected TabSheet tradeTabSheet;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        tradeTabSheet.getTab("pnlTab").setVisible(false);
    }
}