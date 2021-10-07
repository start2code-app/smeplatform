package com.gcs.gcsplatform.web.screens.location;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Location;

@UiController("gcsplatform_Location.edit")
@UiDescriptor("location-edit.xml")
@EditedEntityContainer("locationDc")
@LoadDataBeforeShow
public class LocationEdit extends StandardEditor<Location> {
}