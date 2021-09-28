package com.gcs.gcsplatform.service.report;

import java.util.List;
import java.util.Map;

import com.gcs.gcsplatform.entity.invoice.Invoice;

public interface InvoiceReportService {

    String NAME = "gcsplatform_InvoiceReportService";

    /**
     * Generates header band data for invoice report.
     *
     * @param invoice Invoice
     * @return Header band data
     */
    @SuppressWarnings("unused")
    List<Map<String, Object>> getHeader(Invoice invoice);

    /**
     * Generates lines band data for invoice report.
     *
     * @param invoice Invoice
     * @return Lines band data
     */
    @SuppressWarnings("unused")
    List<Map<String, Object>> getInvoiceLines(Invoice invoice);

    /**
     * Generates bottom band data for invoice report.
     *
     * @param invoice Invoice
     * @return Bottom band data
     */
    @SuppressWarnings("unused")
    List<Map<String, Object>> getBottom(Invoice invoice);
}