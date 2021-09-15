package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;

public interface FxCalculationService {

    String NAME = "gcsplatform_FxCalculationService";

    /**
     * Calculates GBP equivalent dividing specified amount in foreign currency by specified GBP exchange rate
     * against foreign currency.
     * <p>
     * Returns zero if any of arguments is zero.
     *
     * @param amount  Amount in foreign currency
     * @param fxValue GBP to foreign currency exchange rate
     * @return GBP equivalent
     */
    BigDecimal calculateGbpEquivalent(BigDecimal amount, BigDecimal fxValue);

    /**
     * Calculates USD equivalent multiplying specified amount in GBP by specified GBP exchange rate against USD.
     * <p>
     * Returns zero if any of arguments is zero.
     *
     * @param gbpAmount  GBP amount
     * @param usdFxValue GBP to USD exchange rate
     * @return USD equivalent
     */
    BigDecimal calculateUsdEquivalent(BigDecimal gbpAmount, BigDecimal usdFxValue);
}