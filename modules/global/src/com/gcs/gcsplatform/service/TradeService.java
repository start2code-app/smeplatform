package com.gcs.gcsplatform.service;

import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.trade.Trade;

public interface TradeService {

    String NAME = "gcsplatform_TradeService";

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * Selects trades in date interval.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, tradeCurrency.
     *
     * @param tradeClass Trade class
     * @param startDate  Trade date from
     * @param endDate    Trade date to
     * @return List of trades
     */
    <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, @Nullable Date startDate,
            @Nullable Date endDate);

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, tradeCurrency.
     *
     * @param tradeClass Trade class
     * @return List of trades
     */
    <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass);
}