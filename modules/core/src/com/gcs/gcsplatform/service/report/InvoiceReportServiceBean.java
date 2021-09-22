package com.gcs.gcsplatform.service.report;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.service.invoice.InvoiceLineService;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Service;

@Service(InvoiceReportService.NAME)
public class InvoiceReportServiceBean implements InvoiceReportService {

    @Inject
    private InvoiceLineService invoiceLineService;
    @Inject
    private ReportDataConversionService reportDataConversionService;

    @Override
    public List<Map<String, Object>> getInvoiceLines(Invoice invoice) {
        Collection<InvoiceLine> invoiceLines = invoiceLineService.getInvoiceLines(invoice,
                ViewBuilder.of(Invoice.class)
                        .addView(View.LOCAL)
                        .build());
        List<Map<String, Object>> mapList = reportDataConversionService.entityCollectionToMapList(invoiceLines);
        for (int i = 0; i < mapList.size(); i++) {
            mapList.get(i).put("rowNum", i + 1);
        }
        return mapList;
    }
}