package com.gcs.gcsplatform.web.screens.closedtrade;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;

@UiController("gcsplatform_ClosedTrade.edit")
@UiDescriptor("closed-trade-edit.xml")
@EditedEntityContainer("closedTradeDc")
@LoadDataBeforeShow
public class ClosedTradeEdit extends StandardEditor<ClosedTrade> {
}