package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultBoolean;

@Source(type = SourceType.DATABASE)
public interface SnapshotConfig extends Config {

    /**
     * @return Enables snapshot date interval selection dialog.
     */
    @Property("snapshot.dateIntervalSelection")
    @DefaultBoolean(value = false)
    Boolean getDateIntervalSelection();
}
