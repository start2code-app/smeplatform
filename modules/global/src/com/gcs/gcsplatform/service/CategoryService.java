package com.gcs.gcsplatform.service;

import java.util.List;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.haulmont.cuba.core.global.View;

public interface CategoryService {

    String NAME = "gcsplatform_CategoryService";

    /**
     * Gets all the categories.
     *
     * @param view - View
     * @return List of categories
     */
    List<Category> getCategories(View view);
}