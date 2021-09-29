package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultLong;

@Source(type = SourceType.DATABASE)
public interface FxConfig extends Config {

    /**
     * Alpha Vantage servoce api key.
     *
     * @return Api key
     */
    @Property(value = "fx.alphaVantage.apiKey")
    String getAlphaVantageApiKey();

    /**
     * Delay between requests in seconds.
     *
     * @return delay
     */
    @Property(value = "fx.delay")
    @DefaultLong(value = 20)
    long getDelay();
}
