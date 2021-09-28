package com.gcs.gcsplatform.service.trade;

import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.core.global.View;

public interface TradeService {

    String NAME = "gcsplatform_TradeService";

    /**
     * Gets list of trades in specified date interval.
     *
     * @param tradeClass Trade class
     * @param startDate  Trade date from
     * @param endDate    Trade date to
     * @param view       View
     * @return List of trades
     */
    <T extends Trade> Collection<T> getTrades(Class<T> tradeClass, @Nullable Date startDate, @Nullable Date endDate,
            View view);

    /**
     * Gets list of trades.
     *
     * @param tradeClass Trade class
     * @param view       View
     * @return List of trades
     */
    <T extends Trade> Collection<T> getTrades(Class<T> tradeClass, View view);

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * Selects trades in date interval.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, currency.
     *
     * @param tradeClass Trade class
     * @param startDate  Trade date from
     * @param endDate    Trade date to
     * @param view       View
     * @return List of trades
     */
    <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, @Nullable Date startDate,
            @Nullable Date endDate, View view);

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, currency.
     *
     * @param tradeClass Trade class
     * @param view       View
     * @return List of trades
     */
    <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view);

    /**
     * Gets list of closed trades with specified currency and month.
     *
     * @param tradeClass Trade class
     * @param currency   Trade currency
     * @param startDate  Trade date from
     * @param endDate    Trade date to
     * @param view       View
     * @return List of trades
     */
    <T extends Trade> Collection<T> getTradesByCurrency(Class<T> tradeClass, String currency,
            @Nullable Date startDate, @Nullable Date endDate, View view);

    /**
     * Searches for a trade with specified class and contract number.
     *
     * @param tradeClass Trade class
     * @param traderef   Contract number
     * @param view       View
     * @param <T>        Trade type
     * @return Trade or null
     */
    @Nullable
    <T extends Trade> T findTrade(Class<T> tradeClass, String traderef, View view);
}