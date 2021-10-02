package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;

public interface InvoiceWorkDocsPublishService {

    String NAME = "gcsplatform_InvoiceWorkDocsPublishService";

    /**
     * Publishes an invoice to WorkDocs.
     * <p>
     * Puts PDF and XLSX files to folder depending on invoice location configured in Invoice Company entity.
     *
     * @param invoice Invoice
     * @return Invoice publish error or null
     */
    @Nullable
    InvoicePublishError publishToWorkDocs(Invoice invoice);
}