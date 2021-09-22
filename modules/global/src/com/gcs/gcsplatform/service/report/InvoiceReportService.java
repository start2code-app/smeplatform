package com.gcs.gcsplatform.service.report;

import java.util.List;
import java.util.Map;

import com.gcs.gcsplatform.entity.invoice.Invoice;

public interface InvoiceReportService {

    String NAME = "gcsplatform_InvoiceReportService";

    List<Map<String, Object>> getHeader(Invoice invoice);

    List<Map<String, Object>> getInvoiceLines(Invoice invoice);

    List<Map<String, Object>> getBottom(Invoice invoice);
}