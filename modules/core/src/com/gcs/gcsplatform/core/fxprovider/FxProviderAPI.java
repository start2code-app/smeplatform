package com.gcs.gcsplatform.core.fxprovider;

import java.math.BigDecimal;

public interface FxProviderAPI {

    String NAME = "gcsplatform_FxProvider";

    /**
     * Gets actual FX from external service.
     *
     * @param fromCurrency From currency code
     * @param toCurrency   To currency code
     * @return FX value
     */
    BigDecimal getFx(String fromCurrency, String toCurrency);
}