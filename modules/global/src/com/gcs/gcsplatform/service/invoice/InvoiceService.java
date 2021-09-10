package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.haulmont.cuba.core.global.View;

public interface InvoiceService {

    String NAME = "gcsplatform_InvoiceService";

    /**
     * Creates invoices grouping specified invoice lines by currency, location, counterparty code, start date.
     *
     * @param invoiceLines Invoice lines
     * @return Invoices
     */
    Collection<Invoice> createInvoices(Collection<InvoiceLine> invoiceLines);

    /**
     * Creates invoice by specified invoice line.
     *
     * @param invoiceLine Invoice line
     * @return Invoice
     */
    Invoice createInvoice(InvoiceLine invoiceLine);

    /**
     * Calculates invoice amount and gbp equivalent by existing invoice lines.
     * Increments issue attribute by 1.
     *
     * @param invoice Invoice
     * @return Recalculated invoice
     */
    Invoice calculateAmount(Invoice invoice);

    /**
     * Searches for a corresponding invoice by currency, location, counterparty code, start date.
     *
     * @param invoiceLine Invoice line
     * @param view        View
     * @return invoice if exists or null
     */
    @Nullable
    Invoice findInvoice(InvoiceLine invoiceLine, View view);
}