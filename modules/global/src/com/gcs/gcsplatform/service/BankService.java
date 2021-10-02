package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.InvoiceBank;
import com.haulmont.cuba.core.global.View;

public interface BankService {

    String NAME = "gcsplatform_BankService";

    /**
     * Searches for a bank with specified location and currency.
     *
     * @param location Location (eg. "LON" or "HK")
     * @param currency Currency
     * @param view     View
     * @return Bank or null
     */
    @Nullable
    InvoiceBank findInvoiceBank(String location, String currency, View view);

    boolean bankExists(String location, String currency);
}