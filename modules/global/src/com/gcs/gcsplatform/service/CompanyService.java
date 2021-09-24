package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Company;
import com.haulmont.cuba.core.global.View;

public interface CompanyService {

    String NAME = "gcsplatform_CompanyService";

    /**
     * Searches for a company with specified location.
     *
     * @param location Location (eg. "LON" or "HK")
     * @param view     View
     * @return Company or null
     */
    @Nullable
    Company findCompany(String location, View view);
}