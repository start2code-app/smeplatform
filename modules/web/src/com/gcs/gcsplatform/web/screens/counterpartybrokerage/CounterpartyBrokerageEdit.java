package com.gcs.gcsplatform.web.screens.counterpartybrokerage;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;

@UiController("gcsplatform_CounterpartyBrokerage.edit")
@UiDescriptor("counterparty-brokerage-edit.xml")
@EditedEntityContainer("counterpartyBrokerageDc")
@LoadDataBeforeShow
public class CounterpartyBrokerageEdit extends StandardEditor<CounterpartyBrokerage> {
}