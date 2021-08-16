package com.gcs.gcsplatform.web.screens.fx;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Fx;

@UiController("gcsplatform_Fx.browse")
@UiDescriptor("fx-browse.xml")
@LookupComponent("fxesTable")
@LoadDataBeforeShow
public class FxBrowse extends StandardLookup<Fx> {
}