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
     * Checks whether a snapshot for specified interval was taken already.
     *
     * @param startDate Start date
     * @param endDate End date
     * @return true if invoice lines exist for previous month
     */
    boolean snapshotIsTaken(Date startDate, Date endDate);

    /**
     * Removes invoices and invoice lines created for specified interval.
     *
     * @param startDate Start date
     * @param endDate End date
     */
    void clearSnapshot(Date startDate, Date endDate);
}