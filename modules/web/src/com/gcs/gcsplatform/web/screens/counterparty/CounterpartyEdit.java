package com.gcs.gcsplatform.web.screens.counterparty;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;

@UiController("gcsplatform_Counterparty.edit")
@UiDescriptor("counterparty-edit.xml")
@EditedEntityContainer("counterpartyDc")
@LoadDataBeforeShow
public class CounterpartyEdit extends StandardEditor<Counterparty> {
}