package com.gcs.gcsplatform.web.screens.trade.tradecontainer;

import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.haulmont.cuba.gui.screen.DialogMode;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiDescriptor("trade-container-edit.xml")
@EditedEntityContainer("tradeContainerDc")
@LoadDataBeforeShow
@DialogMode(forceDialog = true)
public abstract class TradeContainerEdit<T extends TradeContainer> extends StandardEditor<T> {
}