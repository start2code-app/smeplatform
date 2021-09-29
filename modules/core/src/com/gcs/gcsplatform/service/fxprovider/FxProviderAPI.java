package com.gcs.gcsplatform.service.fxprovider;

import java.math.BigDecimal;

public interface FxProviderAPI {

    String NAME = "gcsplatform_FxProvider";

    BigDecimal getFx(String fromCurrency, String toCurrency);
}