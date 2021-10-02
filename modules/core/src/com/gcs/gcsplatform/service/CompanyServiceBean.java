package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.InvoiceCompany;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(CompanyService.NAME)
public class CompanyServiceBean implements CompanyService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public InvoiceCompany findInvoiceCompany(String location, View view) {
        return dataManager.load(InvoiceCompany.class)
                .query("select e from gcsplatform_InvoiceCompany e "
                        + "where e.location.name = :location")
                .parameter("location", location)
                .cacheable(true)
                .view(view)
                .optional()
                .orElse(null);
    }
}