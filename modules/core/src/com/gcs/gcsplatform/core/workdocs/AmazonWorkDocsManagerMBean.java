package com.gcs.gcsplatform.core.workdocs;

import com.haulmont.cuba.core.sys.jmx.JmxBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@JmxBean(module = "gcsplatform", alias = "AmazonWorkDocsManager")
@ManagedResource(description = "JMX interface for managing Amazon WorkDocs client")
public interface AmazonWorkDocsManagerMBean {

    @ManagedOperation(description = "Refreshes Amazon WorkDocs client")
    String refreshWorkDocsClient();
}
