package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedTrade.browse")
@UiDescriptor("closed-trade-browse.xml")
public class ClosedTradeBrowse extends TradeContainerBrowse<ClosedTrade> {
}