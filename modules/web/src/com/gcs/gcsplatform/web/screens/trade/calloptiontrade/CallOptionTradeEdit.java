package com.gcs.gcsplatform.web.screens.trade.calloptiontrade;

import com.gcs.gcsplatform.entity.trade.CallOptionTrade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerEdit;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_CallOptionTrade.edit")
@UiDescriptor("call-option-trade-edit.xml")
public class CallOptionTradeEdit extends TradeContainerEdit<CallOptionTrade> {
}