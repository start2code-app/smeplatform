package com.gcs.gcsplatform.service;

import java.math.BigDecimal;

public interface FxService {

    String NAME = "gcsplatform_FxService";

    /**
     * Gets foreign exchange rate value of the current month and year. If no such, returns zero.
     *
     * @param currency - Foreign currency
     * @return Foreign currency rate or zero
     */
    BigDecimal getFxValue(String currency);
}