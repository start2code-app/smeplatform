package com.gcs.gcsplatform.service.invoice;

import com.gcs.gcsplatform.entity.invoice.Invoice;

public interface InvoicePrintService {

    String NAME = "gcsplatform_InvoicePrintService";

    /**
     * Prints an invoice and saves it to the file storage.
     *
     * @param invoice Invoice
     */
    void print(Invoice invoice);
}