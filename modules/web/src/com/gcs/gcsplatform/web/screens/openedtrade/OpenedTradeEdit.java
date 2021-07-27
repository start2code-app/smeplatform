package com.gcs.gcsplatform.web.screens.openedtrade;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;

@UiController("gcsplatform_OpenedTrade.edit")
@UiDescriptor("opened-trade-edit.xml")
@EditedEntityContainer("openedTradeDc")
@LoadDataBeforeShow
public class OpenedTradeEdit extends StandardEditor<OpenedTrade> {
}