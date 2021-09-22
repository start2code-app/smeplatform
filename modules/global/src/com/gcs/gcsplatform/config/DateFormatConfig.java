package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;

@Source(type = SourceType.DATABASE)
public interface DateFormatConfig extends Config {

    @Property("dateFormat.default")
    @DefaultString("dd/MM/yyyy")
    String getDefaultDateFormat();
}
