package com.gcs.gcsplatform.service;

import java.util.Date;

import com.gcs.gcsplatform.entity.trade.TradeContainer;

public interface CloseTradeService {

    String NAME = "gcsplatform_CloseTradeService";

    /**
     * Creates ClosedTrade instance based on provided trade and then removes original trade.
     *
     * @param tradeContainer - Original trade
     * @param maturityDate   - Maturity date that sets to newly created ClosedTrade instance
     * @param <T>            - Trade type
     */
    <T extends TradeContainer> void close(T tradeContainer, Date maturityDate);

    /**
     * Creates ClosedTrade instance based on provided trade and then generates new contract number for original trade.
     *
     * @param tradeContainer - Original trade
     * @param maturityDate   - Maturity date that sets to newly created ClosedTrade instance
     * @param <T>            - Trade type
     */
    <T extends TradeContainer> void closeReopen(T tradeContainer, Date maturityDate);
}