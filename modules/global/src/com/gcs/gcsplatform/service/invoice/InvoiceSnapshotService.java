package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
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
     * @return true if invoice lines or invoices exist for previous month
     */
    boolean snapshotIsTaken();

    /**
     * Removes invoices and invoice lines created for previous month.
     */
    void clearSnapshot();
}