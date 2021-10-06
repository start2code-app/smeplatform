package com.gcs.gcsplatform.config;

import java.util.Date;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.type.DateFactory;
import com.haulmont.cuba.core.config.type.DateStringify;
import com.haulmont.cuba.core.config.type.Factory;
import com.haulmont.cuba.core.config.type.StringListStringify;
import com.haulmont.cuba.core.config.type.StringListTypeFactory;
import com.haulmont.cuba.core.config.type.Stringify;

@Source(type = SourceType.DATABASE)
public interface SnapshotConfig extends Config {

    /**
     * @return Snapshot month.
     */
    @Property("snapshot.month")
    @Factory(factory = DateFactory.class)
    @Stringify(stringify = DateStringify.class)
    Date getSnapshotMonth();
}
