package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isAnyNullOrZero;

@Service(FxCalculationService.NAME)
public class FxCalculationServiceBean implements FxCalculationService {

    @Override
    public BigDecimal calculateGbpEquivalent(BigDecimal amount, BigDecimal fxValue) {
        if (isAnyNullOrZero(amount, fxValue)) {
            return BigDecimal.ZERO;
        }
        return amount.divide(fxValue, 2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateUsdEquivalent(BigDecimal gbpAmount, BigDecimal usdFxValue) {
        if (isAnyNullOrZero(gbpAmount, usdFxValue)) {
            return BigDecimal.ZERO;
        }
        return gbpAmount.multiply(usdFxValue);
    }

    @Override
    public BigDecimal calculateFxAgainstUsd(BigDecimal fx, BigDecimal usdFx) {
        if (isAnyNullOrZero(fx, usdFx)) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ONE
                .divide(fx, 4, RoundingMode.HALF_UP)
                .multiply(usdFx);
    }
}