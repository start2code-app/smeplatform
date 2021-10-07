package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.Invoice;

public interface InvoicePrintService {

    String NAME = "gcsplatform_InvoicePrintService";

    /**
     * Prints an invoice and saves it to the file storage.
     *
     * @param invoice Invoice
     */
    void print(Invoice invoice);

    /**
     * Prints invoices and saves them to the file storage.
     *
     * @param invoices Invoices
     */
    void print(Collection<Invoice> invoices);
}