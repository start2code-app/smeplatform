package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.Invoice;

public interface InvoicePrintService {

    String NAME = "gcsplatform_InvoicePrintService";

    /**
     * Prints invoices and saves them in file storage.
     *
     * @param invoices Invoices
     */
    void print(Collection<Invoice> invoices);
}