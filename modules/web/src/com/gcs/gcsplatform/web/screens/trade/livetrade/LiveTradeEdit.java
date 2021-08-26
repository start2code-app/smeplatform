package com.gcs.gcsplatform.web.screens.trade.livetrade;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.edit")
@UiDescriptor("live-trade-edit.xml")
public class LiveTradeEdit extends TradeEdit<LiveTrade> {
}