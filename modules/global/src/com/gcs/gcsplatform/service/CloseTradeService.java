package com.gcs.gcsplatform.service;

import java.util.Date;

import com.gcs.gcsplatform.entity.trade.TradeContainer;

public interface CloseTradeService {

    String NAME = "gcsplatform_CloseTradeService";

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then removes original trade.
     *
     * @param tradeContainer - Original trade
     * @param maturityDate   - Maturity date that sets to newly created ClosedTrade instance
     */
    void close(TradeContainer tradeContainer, Date maturityDate);

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then generates new contract number for
     * original trade.
     *
     * @param tradeContainer - Original trade
     * @param maturityDate   - Maturity date that sets to newly created ClosedTrade instance. Also sets to Value date of
     *                       original trade
     */
    void closeReopen(TradeContainer tradeContainer, Date maturityDate);
}