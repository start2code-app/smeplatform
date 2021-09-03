package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;

public interface PnlCalculationService {

    String NAME = "gcsplatform_PnlCalculationService";

    /**
     * Calculates PNL. Returns zero if any of numeric arguments is null or zero.
     *
     * @param numdays    Number of days
     * @param nominal    Nominal
     * @param brokerage  Brokerage
     * @param xrate      Xrate
     * @param startPrice Start price
     * @param cash       Cash
     * @return PNL
     */
    BigDecimal calculatePnl(Long numdays, BigDecimal nominal, BigDecimal brokerage, BigDecimal xrate,
            BigDecimal startPrice, Boolean cash);

    /**
     * Calculates FX amount equivalent. Returns zero if any of arguments is zero.
     *
     * @param amount  Amount
     * @param fxValue Foreign exchange value
     * @return FX amount equivalent
     */
    BigDecimal calculateFxEquivalent(BigDecimal amount, BigDecimal fxValue);
}