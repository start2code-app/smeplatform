package com.gcs.gcsplatform.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.entity.ReportTemplate;

@Source(type = SourceType.DATABASE)
public interface ReportConfig extends Config {

    @Property("report.invoice")
    @Default("report$Report-d12df763-05d9-8b29-b00f-725e94143b7b")
    Report getInvoiceReport();

    @Property("report.invoiceExcelTemplate")
    @Default("report$ReportTemplate-8229d539-d84a-8b1a-156e-dc6305f5b230")
    ReportTemplate getInvoiceExcelTemplate();

    @Property("report.invoiceHtmlTemplate")
    @Default("report$ReportTemplate-8a6ea2e9-67e5-313c-54ef-799451cb590e")
    ReportTemplate getInvoiceHtmlTemplate();
}
