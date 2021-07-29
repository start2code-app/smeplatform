package com.gcs.gcsplatform.web.screens.dealer;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Dealer;

@UiController("gcsplatform_Dealer.edit")
@UiDescriptor("dealer-edit.xml")
@EditedEntityContainer("dealerDc")
@LoadDataBeforeShow
public class DealerEdit extends StandardEditor<Dealer> {
}