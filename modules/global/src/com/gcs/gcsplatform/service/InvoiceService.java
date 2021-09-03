package com.gcs.gcsplatform.service;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;

public interface InvoiceService {

    String NAME = "gcsplatform_InvoiceService";

    Invoice createInvoice(InvoiceLine invoiceLine);

    Collection<InvoiceLine> splitTrade(ClosedTrade trade);
}