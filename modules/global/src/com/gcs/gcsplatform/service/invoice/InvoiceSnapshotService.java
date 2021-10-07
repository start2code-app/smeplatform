package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import java.util.Date;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;

public interface InvoiceSnapshotService {

    String NAME = "gcsplatform_InvoiceSnapshotService";

    /**
     * Creates snapshot (invoices and invoice lines) for specified trades.
     *
     * @param trades Trades
     */
    void makeSnapshot(Collection<ClosedTrade> trades);

    /**
     * Checks whether a snapshot for previous month was taken already.
     *
     * @param month Snapshot month
     * @return true if invoice lines exist for previous month
     */
    boolean snapshotIsTaken(Date month);

    /**
     * Removes invoices and invoice lines created for previous month.
     *
     * @param month Snapshot month
     */
    void clearSnapshot(Date month);
}