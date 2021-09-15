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
        return amount.divide(fxValue, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal calculateUsdEquivalent(BigDecimal gbpAmount, BigDecimal usdFxValue) {
        if (isAnyNullOrZero(gbpAmount, usdFxValue)) {
            return BigDecimal.ZERO;
        }
        return gbpAmount.multiply(usdFxValue);
    }
}