package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.edit")
@UiDescriptor("opened-trade-edit.xml")
public class OpenedTradeEdit extends TradeEdit<OpenedTrade> {
}