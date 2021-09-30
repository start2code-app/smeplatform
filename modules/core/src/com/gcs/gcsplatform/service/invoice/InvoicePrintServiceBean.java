package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.gcs.gcsplatform.config.ReportConfig;
import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.entity.ReportTemplate;
import org.springframework.stereotype.Service;

@Service(InvoicePrintService.NAME)
public class InvoicePrintServiceBean implements InvoicePrintService {

    @Inject
    protected DataManager dataManager;
    @Inject
    protected ReportService reportService;
    @Inject
    protected ReportConfig reportConfig;

    @Override
    public void print(Collection<Invoice> invoices) {
        ReportTemplate htmlTemplate = reportConfig.getInvoiceHtmlTemplate();
        ReportTemplate excelTemplate = reportConfig.getInvoiceExcelTemplate();
        Report report = reportConfig.getInvoiceReport();
        CommitContext commitContext = new CommitContext();
        for (Invoice invoice : invoices) {
            Map<String, Object> params = new HashMap<>();
            params.put("invoice", invoice);
            FileDescriptor pdf = reportService.createAndSaveReport(report, htmlTemplate, params,
                    invoice.getInvoiceNumber());
            FileDescriptor xlsx = reportService.createAndSaveReport(report, excelTemplate, params,
                    invoice.getInvoiceNumber());
            invoice.setPdfFile(pdf);
            invoice.setXlsxFile(xlsx);
            commitContext.addInstanceToCommit(invoice);
        }
        dataManager.commit(commitContext);
    }
}