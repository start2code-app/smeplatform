package com.gcs.gcsplatform.web.screens.trade.closedlivetrade;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedLiveTrade.browse")
@UiDescriptor("closed-live-trade-browse.xml")
public class ClosedLiveTradeBrowse extends TradeContainerBrowse<ClosedTrade> {
}