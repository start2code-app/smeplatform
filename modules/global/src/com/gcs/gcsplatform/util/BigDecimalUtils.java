package com.gcs.gcsplatform.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;

public class BigDecimalUtils {

    public static boolean isNotNullOrZero(BigDecimal number) {
        return number != null && BigDecimal.ZERO.compareTo(number) != 0;
    }

    public static boolean isNullOrZero(BigDecimal number) {
        return !isNotNullOrZero(number);
    }

    public static boolean isAnyNullOrZero(BigDecimal... numbers) {
        if (ArrayUtils.isEmpty(numbers)) {
            return false;
        }
        for (BigDecimal number : numbers) {
            if (isNullOrZero(number)) {
                return true;
            }
        }
        return false;
    }

    public static BigDecimal getNumberOrNull(BigDecimal number) {
        if (number == null) {
            return BigDecimal.ZERO;
        }
        return number;
    }
}