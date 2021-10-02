package com.gcs.gcsplatform.config;

import com.gcs.gcsplatform.entity.masterdata.Location;
import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;

@Source(type = SourceType.DATABASE)
public interface LocationConfig extends Config {

    /**
     * @return London location.
     */
    @Property("location.lon")
    @Default("gcsplatform_Location-2f069ce3-85c5-42ff-b582-bcbb370f33b4")
    Location getLondonLocation();
}
