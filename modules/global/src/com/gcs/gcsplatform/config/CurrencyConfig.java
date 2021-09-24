package com.gcs.gcsplatform.config;

import java.util.List;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.type.Factory;
import com.haulmont.cuba.core.config.type.StringListStringify;
import com.haulmont.cuba.core.config.type.StringListTypeFactory;
import com.haulmont.cuba.core.config.type.Stringify;

@Source(type = SourceType.DATABASE)
public interface CurrencyConfig extends Config {

    /**
     * USD currency.
     *
     * @return USD currency entity
     */
    @Property("currency.usd")
    @Default("gcsplatform_Currency-0fded12e-3c90-4fa4-99ae-6e203c2fa899")
    Currency getUsdCurrency();

    /**
     * Default currencies list.
     *
     * @return List of currencies
     */
    @Property("currency.defaultList")
    @Factory(factory = StringListTypeFactory.class)
    @Stringify(stringify = StringListStringify.class)
    @Default("USD|EUR|GBP")
    List<String> getDefaultCurrencies();
}
