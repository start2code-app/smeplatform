package com.gcs.gcsplatform.web.screens.fx;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Fx;

@UiController("gcsplatform_Fx.edit")
@UiDescriptor("fx-edit.xml")
@EditedEntityContainer("fxDc")
@LoadDataBeforeShow
public class FxEdit extends StandardEditor<Fx> {
}