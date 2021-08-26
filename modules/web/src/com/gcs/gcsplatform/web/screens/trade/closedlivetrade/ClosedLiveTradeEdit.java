package com.gcs.gcsplatform.web.screens.trade.closedlivetrade;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedLiveTrade.edit")
@UiDescriptor("closed-live-trade-edit.xml")
public class ClosedLiveTradeEdit extends TradeEdit<OpenedTrade> {
}