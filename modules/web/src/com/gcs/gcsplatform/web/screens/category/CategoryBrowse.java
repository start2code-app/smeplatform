package com.gcs.gcsplatform.web.screens.category;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Category;

@UiController("gcsplatform_Category.browse")
@UiDescriptor("category-browse.xml")
@LookupComponent("categoriesTable")
@LoadDataBeforeShow
public class CategoryBrowse extends StandardLookup<Category> {
}