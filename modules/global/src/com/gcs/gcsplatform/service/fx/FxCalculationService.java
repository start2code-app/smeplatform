package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;

public interface FxCalculationService {

    String NAME = "gcsplatform_FxCalculationService";

    /**
     * Calculates FX amount equivalent. Returns zero if any of arguments is zero.
     *
     * @param amount  Amount
     * @param fxValue Foreign exchange value
     * @return FX amount equivalent
     */
    BigDecimal calculateFxEquivalent(BigDecimal amount, BigDecimal fxValue);
}