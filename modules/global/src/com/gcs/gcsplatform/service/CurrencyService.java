package com.gcs.gcsplatform.service;

import java.util.List;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.haulmont.cuba.core.global.View;

public interface CurrencyService {

    String NAME = "gcsplatform_CurrencyService";

    /**
     * Gets all currencies.
     *
     * @param view View
     * @return List of currencies
     */
    List<Currency> getCurrencies(View view);
}