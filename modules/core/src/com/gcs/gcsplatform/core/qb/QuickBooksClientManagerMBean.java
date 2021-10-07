package com.gcs.gcsplatform.core.qb;

import com.haulmont.cuba.core.sys.jmx.JmxBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@JmxBean(module = "gcsplatform", alias = "QuickBooksClientManager")
@ManagedResource(description = "JMX interface for managing QuickBooks client")
public interface QuickBooksClientManagerMBean {

    @ManagedOperation(description = "Refreshes QuickBooks client")
    String refreshQuickBooksClient();
}
