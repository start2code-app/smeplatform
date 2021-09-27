package com.gcs.gcsplatform.web.screens.broker;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Broker;

@UiController("gcsplatform_Broker.browse")
@UiDescriptor("broker-browse.xml")
@LookupComponent("brokersTable")
@LoadDataBeforeShow
public class BrokerBrowse extends StandardLookup<Broker> {
}