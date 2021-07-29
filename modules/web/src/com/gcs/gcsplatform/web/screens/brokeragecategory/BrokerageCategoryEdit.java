package com.gcs.gcsplatform.web.screens.brokeragecategory;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.BrokerageCategory;

@UiController("gcsplatform_BrokerageCategory.edit")
@UiDescriptor("brokerage-category-edit.xml")
@EditedEntityContainer("brokerageCategoryDc")
@LoadDataBeforeShow
public class BrokerageCategoryEdit extends StandardEditor<BrokerageCategory> {
}