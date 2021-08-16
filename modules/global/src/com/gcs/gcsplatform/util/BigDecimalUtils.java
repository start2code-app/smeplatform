package com.gcs.gcsplatform.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static boolean isNotNullOrZero(BigDecimal number) {
        return number != null && BigDecimal.ZERO.compareTo(number) != 0;
    }

    public static boolean isNullOrZero(BigDecimal number) {
        return !isNotNullOrZero(number);
    }
}