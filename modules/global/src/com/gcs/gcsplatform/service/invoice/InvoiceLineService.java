package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;

public interface InvoiceLineService {

    String NAME = "gcsplatform_InvoiceLineService";

    /**
     * Split trade onto two invoice lines representing buy side and sell side.
     *
     * @param trade Trade
     * @return Collection of invoice lines
     */
    Collection<InvoiceLine> splitTrade(ClosedTrade trade);
}