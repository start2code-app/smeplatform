package com.gcs.gcsplatform.service;

import java.util.Collection;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;

public interface OpenedTradeService {

    String NAME = "gcsplatform_OpenedTradeService";

    /**
     * Gets opened trades and calculates pnl/gbp equivalent assuming maturity date is today.
     *
     * @return Opened trades
     */
    Collection<OpenedTrade> getOpenedTradesForPnlChart();
}