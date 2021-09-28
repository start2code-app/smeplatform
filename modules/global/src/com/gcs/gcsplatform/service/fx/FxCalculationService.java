package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;

import com.gcs.gcsplatform.entity.trade.Trade;

public interface FxCalculationService {

    String NAME = "gcsplatform_FxCalculationService";

    /**
     * Calculates GBP equivalent dividing specified amount in foreign currency by specified GBP exchange rate
     * against foreign currency.
     * <p>
     * Returns zero if any of arguments is zero.
     *
     * @param amount Amount in foreign currency
     * @param fx     GBP to foreign currency exchange rate
     * @return GBP equivalent
     */
    BigDecimal calculateGbpEquivalent(BigDecimal amount, BigDecimal fx);

    /**
     * Calculates equivalent via cross rate.
     * <p>
     * Returns zero if any of arguments is zero.
     *
     * @param amount   Amount in foreign currency
     * @param baseFx   GBP to amount currency exchange rate
     * @param targetFx GBP to target currency exchange rate
     * @return Equivalent
     */
    BigDecimal calculateEquivalent(BigDecimal amount, BigDecimal baseFx, BigDecimal targetFx);

    /**
     * Calculates cross rate for specified trade.
     * <p>
     * If bond currency = repo currency, returns 1.
     * <p>
     * If bond currency is one of USD/EUR/GBP, returns 1.
     * <p>
     * If bond currency is other than USD/EUR/GBP, returns cross rate between bond currency and repo currency.
     * <p>
     * If bond currency or repo currency is null, returns 0.
     *
     * @param trade Trade
     * @return Cross rate
     */
    BigDecimal calculateCrossRate(Trade trade);

    /**
     * Calculates cross rate between two currencies.
     * <p>
     * Returns zero if any of arguments is zero.
     *
     * @param baseFx   Base currency FX
     * @param targetFx Target currency FX
     * @return Cross rate
     */
    BigDecimal calculateCrossRate(BigDecimal baseFx, BigDecimal targetFx);
}