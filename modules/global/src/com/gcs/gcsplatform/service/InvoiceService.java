package com.gcs.gcsplatform.service;

import java.util.Collection;
import java.util.List;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.trade.Trade;

public interface InvoiceService {

    String NAME = "gcsplatform_InvoiceService";

    Collection<Invoice> createInvoices(Collection<?extends Trade> trades);
}