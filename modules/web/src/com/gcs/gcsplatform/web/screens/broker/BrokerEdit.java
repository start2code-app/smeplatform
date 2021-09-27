package com.gcs.gcsplatform.web.screens.broker;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Broker;

@UiController("gcsplatform_Broker.edit")
@UiDescriptor("broker-edit.xml")
@EditedEntityContainer("brokerDc")
@LoadDataBeforeShow
public class BrokerEdit extends StandardEditor<Broker> {
}