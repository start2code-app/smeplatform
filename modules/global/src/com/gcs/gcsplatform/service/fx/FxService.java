package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;

public interface FxService {

    String NAME = "gcsplatform_FxService";

    /**
     * Gets foreign exchange rate value of specified date (considers only month and year). If no such, returns null.
     *
     * @param currency    Foreign currency
     * @param billingDate Foreign exchange date
     * @return Foreign exchange rate or null
     */
    @Nullable
    BigDecimal findFxValue(String currency, Date billingDate);

    /**
     * Gets USD exchange rate value of specified date (considers only month and year). If no such, returns null.
     *
     * @param fxDate Foreign exchange date
     * @return USD exchange rate or null
     */
    @Nullable
    BigDecimal findUsdFxValue(Date fxDate);
}