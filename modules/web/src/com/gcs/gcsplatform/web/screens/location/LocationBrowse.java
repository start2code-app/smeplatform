package com.gcs.gcsplatform.web.screens.location;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Location;

@UiController("gcsplatform_Location.browse")
@UiDescriptor("location-browse.xml")
@LookupComponent("locationsTable")
@LoadDataBeforeShow
public class LocationBrowse extends StandardLookup<Location> {
}