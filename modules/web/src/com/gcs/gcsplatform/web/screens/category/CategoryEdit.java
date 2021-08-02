package com.gcs.gcsplatform.web.screens.category;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Category;

@UiController("gcsplatform_Category.edit")
@UiDescriptor("category-edit.xml")
@EditedEntityContainer("categoryDc")
@LoadDataBeforeShow
public class CategoryEdit extends StandardEditor<Category> {
}