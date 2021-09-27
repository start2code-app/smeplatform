package com.gcs.gcsplatform.web.screens.trader;

import com.gcs.gcsplatform.entity.masterdata.Trader;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_Trader.edit")
@UiDescriptor("trader-edit.xml")
@EditedEntityContainer("traderDc")
@LoadDataBeforeShow
public class TraderEdit extends StandardEditor<Trader> {
}