package com.gcs.gcsplatform.config;

import java.math.BigDecimal;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultString;
import com.haulmont.cuba.core.config.type.DateFactory;
import com.haulmont.cuba.core.config.type.DecimalStringify;
import com.haulmont.cuba.core.config.type.Factory;
import com.haulmont.cuba.core.config.type.StringListStringify;
import com.haulmont.cuba.core.config.type.Stringify;

@Source(type = SourceType.DATABASE)
public interface TradeConfig extends Config {

    /**
     * Formatted string for newly generated trade references.
     * Default format: "H000001".
     *
     * @return Format string
     */
    @Property("trade.refGenerationFormat")
    @DefaultString("H%06d")
    String getRefGenerationFormat();

    /**
     * Minimal bar of trade commission in USD equivalent.
     * <p>
     * Applies onto trades having "Commission override" attribute true value and PNL value less than this one in USD
     * equivalent.
     *
     * @return Minimal commission
     */
    @Property("trade.usdMinimalCommission")
    @Stringify(stringify = DecimalStringify.class)
    @Default("2.5")
    BigDecimal getUsdMinimalCommission();
}
