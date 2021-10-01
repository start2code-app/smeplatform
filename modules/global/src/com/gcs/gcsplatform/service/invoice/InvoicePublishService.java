package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.Invoice;

public interface InvoicePublishService {

    String NAME = "gcsplatform_InvoicePublishService";

    /**
     * Publishes invoices to WorkDocs.
     * <p>
     * Puts PDF and XLSX files either to "HK" or "LON" folder considering invoice location.
     *
     * Skips invoices without printed files.
     *
     * @param invoices Invoice
     */
    void publishToWorkDocs(Collection<Invoice> invoices);
}