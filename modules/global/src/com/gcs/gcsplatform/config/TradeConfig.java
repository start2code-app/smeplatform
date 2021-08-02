package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;

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
}
