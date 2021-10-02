package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import java.util.Map;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.ReportConfig;
import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.entity.ReportTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(InvoicePrintService.NAME)
public class InvoicePrintServiceBean implements InvoicePrintService {

    @Inject
    private TransactionalDataManager txDataManager;
    @Inject
    private ReportService reportService;
    @Inject
    private ReportConfig reportConfig;

    @Override
    public void print(Invoice invoice) {
        ReportTemplate htmlTemplate = reportConfig.getInvoiceHtmlTemplate();
        ReportTemplate excelTemplate = reportConfig.getInvoiceExcelTemplate();
        Report report = reportConfig.getInvoiceReport();
        Map<String, Object> params = ParamsMap.of("invoice", invoice);
        FileDescriptor pdf = reportService.createAndSaveReport(report, htmlTemplate, params,
                invoice.getInvoiceNumber());
        FileDescriptor xlsx = reportService.createAndSaveReport(report, excelTemplate, params,
                invoice.getInvoiceNumber());
        invoice.setPdfFile(pdf);
        invoice.setXlsxFile(xlsx);
        txDataManager.save(invoice);
    }

    @Transactional
    @Override
    public void print(Collection<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            print(invoice);
        }
    }
}