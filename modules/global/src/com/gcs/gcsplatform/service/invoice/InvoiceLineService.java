package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.haulmont.cuba.core.global.View;

public interface InvoiceLineService {

    String NAME = "gcsplatform_InvoiceLineService";

    /**
     * Split trade onto two invoice lines representing buy side and sell side.
     *
     * @param trade Trade
     * @return Collection of invoice lines
     */
    Collection<InvoiceLine> splitTrade(ClosedTrade trade);

    /**
     * Gets all invoice lines corresponding to specified invoice.
     *
     * @param invoice Invoice
     * @param view
     * @return List of invoice lines
     */
    Collection<InvoiceLine> getInvoiceLines(Invoice invoice, View view);
}