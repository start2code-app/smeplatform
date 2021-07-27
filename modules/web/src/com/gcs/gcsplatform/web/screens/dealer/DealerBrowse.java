package com.gcs.gcsplatform.web.screens.dealer;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Dealer;

@UiController("gcsplatform_Dealer.browse")
@UiDescriptor("dealer-browse.xml")
@LookupComponent("dealersTable")
@LoadDataBeforeShow
public class DealerBrowse extends StandardLookup<Dealer> {
}