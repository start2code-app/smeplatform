package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
public class OpenedTradeBrowse extends TradeContainerBrowse<OpenedTrade> {
}