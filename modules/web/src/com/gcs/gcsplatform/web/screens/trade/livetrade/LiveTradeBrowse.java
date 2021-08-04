package com.gcs.gcsplatform.web.screens.trade.livetrade;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.browse")
@UiDescriptor("live-trade-browse.xml")
public class LiveTradeBrowse extends TradeContainerBrowse<LiveTrade> {
}