package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;
import com.haulmont.cuba.core.global.Secret;

@Source(type = SourceType.DATABASE)
public interface AmazonWorkDocsConfig extends Config {

    /**
     * @return Amazon WorkDocs access key.
     */
    @Property("amazon.workDocs.accessKey")
    String getAccessKey();

    /**
     * @return Amazon WorkDocs secret access key.
     */
    @Secret
    @Property("amazon.workDocs.secretAccessKey")
    String getSecretAccessKey();

    /**
     * @return Amazon WorkDocs region.
     */
    @Property("amazon.workDocs.region")
    @DefaultString("eu-west-1")
    String getRegion();

    /**
     * @return Amazon WorkDocs folder id for London invoices
     */
    @Property("amazon.workDocs.folder.lon")
    String getLonInvoiceFolder();

    /**
     * @return Amazon WorkDocs folder id for Hong Kong invoices
     */
    @Property("amazon.workDocs.folder.hk")
    String getHkInvoiceFolder();
}