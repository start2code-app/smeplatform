package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Company;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(CompanyService.NAME)
public class CompanyServiceBean implements CompanyService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public Company findCompany(String location, View view) {
        return dataManager.load(Company.class)
                .query("select e from gcsplatform_Company e "
                        + "where e.location = :location")
                .parameter("location", location)
                .view(view)
                .optional()
                .orElse(null);
    }
}