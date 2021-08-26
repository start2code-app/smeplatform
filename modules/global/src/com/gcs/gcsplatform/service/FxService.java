package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;

public interface FxService {

    String NAME = "gcsplatform_FxService";

    /**
     * Gets foreign exchange rate value of specified date (considers only month and year). If no such, returns null.
     *
     * @param currency Foreign currency
     * @param fxDate   Foreign exchange date
     * @return Foreign currency rate or null
     */
    @Nullable
    BigDecimal getFxValue(String currency, Date fxDate);
}