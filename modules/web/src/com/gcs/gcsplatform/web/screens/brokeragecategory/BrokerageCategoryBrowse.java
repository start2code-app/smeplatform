package com.gcs.gcsplatform.web.screens.brokeragecategory;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.BrokerageCategory;

@UiController("gcsplatform_BrokerageCategory.browse")
@UiDescriptor("brokerage-category-browse.xml")
@LookupComponent("brokerageCategoriesTable")
@LoadDataBeforeShow
public class BrokerageCategoryBrowse extends StandardLookup<BrokerageCategory> {
}