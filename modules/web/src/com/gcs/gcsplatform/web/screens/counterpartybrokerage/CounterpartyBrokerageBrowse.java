package com.gcs.gcsplatform.web.screens.counterpartybrokerage;

import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_CounterpartyBrokerage.browse")
@UiDescriptor("counterparty-brokerage-browse.xml")
@LookupComponent("counterpartyBrokeragesTable")
@LoadDataBeforeShow
public class CounterpartyBrokerageBrowse extends StandardLookup<CounterpartyBrokerage> {
}