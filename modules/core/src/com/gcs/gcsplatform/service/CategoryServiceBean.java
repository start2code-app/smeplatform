package com.gcs.gcsplatform.service;

import java.util.List;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(CategoryService.NAME)
public class CategoryServiceBean implements CategoryService {

    @Inject
    private DataManager dataManager;

    @Override
    public List<Category> getCategories(View view) {
        return dataManager.load(Category.class)
                .list();
    }
}