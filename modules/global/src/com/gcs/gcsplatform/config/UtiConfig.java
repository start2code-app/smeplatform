package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;

@Source(type = SourceType.DATABASE)
public interface UtiConfig extends Config {

    /**
     * Formatted string for newly generated UTI.
     * Default format: "U000001".
     *
     * @return Format string
     */
    @Property("uti.generationFormat")
    @DefaultString("U%06d")
    String getUtiGenerationFormat();

    /**
     * UTI constant prefix.
     *
     * @return Prefix
     */
    @Property("uti.prefix")
    @DefaultString("213800AQJHWTFV26G764")
    String getUtiPrefix();
}
