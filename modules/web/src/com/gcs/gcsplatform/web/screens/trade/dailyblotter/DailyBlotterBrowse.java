package com.gcs.gcsplatform.web.screens.trade.dailyblotter;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_DailyBlotter.browse")
@UiDescriptor("daily-blotter-browse.xml")
public class DailyBlotterBrowse extends TradeContainerBrowse<ClosedTrade> {
}