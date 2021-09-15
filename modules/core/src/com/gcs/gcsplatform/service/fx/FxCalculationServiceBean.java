package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isAnyNullOrZero;

@Service(FxCalculationService.NAME)
public class FxCalculationServiceBean implements FxCalculationService {

    @Override
    public BigDecimal calculateFxEquivalent(BigDecimal amount, BigDecimal fxValue) {
        if (isAnyNullOrZero(amount, fxValue)) {
            return BigDecimal.ZERO;
        }
        return amount.divide(fxValue, RoundingMode.HALF_EVEN);
    }
}