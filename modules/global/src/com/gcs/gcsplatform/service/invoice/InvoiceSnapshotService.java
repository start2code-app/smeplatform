package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;

public interface InvoiceSnapshotService {

    String NAME = "gcsplatform_InvoiceSnapshotService";

    /**
     * Creates snapshot (invoices and invoice lines) for specified trades.
     *
     * @param trades Trades
     */
    void makeSnapshot(Collection<ClosedTrade> trades);
}