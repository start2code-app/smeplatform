package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.masterdata.Fx;

public interface FxService {

    String NAME = "gcsplatform_FxService";

    /**
     * Searches foreign exchange of specified date (considers only month and year). If no such, returns null.
     *
     * @param currency    Currency
     * @param billingDate Billing date
     * @return Fx entity
     */
    @Nullable
    Fx findFx(Currency currency, Date billingDate);

    /**
     * Searches foreign exchange rate value of specified date (considers only month and year). If no such, returns null.
     *
     * @param currency    Foreign currency
     * @param billingDate Foreign exchange date
     * @return Foreign exchange rate or null
     */
    @Nullable
    BigDecimal findFxValue(String currency, Date billingDate);

    /**
     * Searches USD exchange rate value of specified date (considers only month and year). If no such, returns null.
     *
     * @param fxDate Foreign exchange date
     * @return USD exchange rate or null
     */
    @Nullable
    BigDecimal findUsdFxValue(Date fxDate);
}