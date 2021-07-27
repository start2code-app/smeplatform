package com.gcs.gcsplatform.web.screens.counterparty;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;

@UiController("gcsplatform_Counterparty.browse")
@UiDescriptor("counterparty-browse.xml")
@LookupComponent("counterpartiesTable")
@LoadDataBeforeShow
public class CounterpartyBrowse extends StandardLookup<Counterparty> {
}