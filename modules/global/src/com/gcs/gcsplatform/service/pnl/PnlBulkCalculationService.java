package com.gcs.gcsplatform.service.pnl;

import java.util.Collection;

import com.gcs.gcsplatform.entity.trade.Trade;

public interface PnlBulkCalculationService {

    String NAME = "gcsplatform_PnlBulkCalculationService";

    /**
     * Calculates pnl/gbp equivalent for specified trades.
     * <p>
     * Gets actual fx value for each invoice month and currency.
     *
     * @param trades Trades
     */
    void bulkCalculatePnl(Collection<? extends Trade> trades);
}