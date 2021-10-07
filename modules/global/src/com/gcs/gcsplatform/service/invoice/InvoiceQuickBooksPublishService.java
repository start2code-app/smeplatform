package com.gcs.gcsplatform.service.invoice;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;

public interface InvoiceQuickBooksPublishService {

    String NAME = "gcsplatform_InvoiceQuickBooksPublishService";

    /**
     * Publishes an invoice to QuickBooks attaching XLSX and PDF files.
     *
     * @param invoice invoice
     * @return Invoice publish error or null
     */
    InvoicePublishError publishToQB(Invoice invoice);
}