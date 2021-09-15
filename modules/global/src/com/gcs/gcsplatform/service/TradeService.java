package com.gcs.gcsplatform.service;

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
     * @param view       View
     * @param startDate  Trade date from
     * @param endDate    Trade date to
     * @return List of trades
     */
    <T extends Trade> Collection<T> getTrades(Class<T> tradeClass, View view, @Nullable Date startDate,
            @Nullable Date endDate);

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * Selects trades in date interval.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, tradeCurrency.
     *
     * @param tradeClass Trade class
     * @param view       View
     * @param startDate  Trade date from
     * @param endDate    Trade date to
     * @return List of trades
     */
    <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view,
            @Nullable Date startDate, @Nullable Date endDate);

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, tradeCurrency.
     *
     * @param tradeClass Trade class
     * @param view       View
     * @return List of trades
     */
    <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view);

    /**
     * Gets list of closed trades with specified currency and month.
     *
     * @param currency    Trade currency
     * @param billingDate FX billing month
     * @param view        View
     * @return List of trades
     */
    Collection<ClosedTrade> getClosedTradesToUpdateFx(String currency, Date billingDate, View view);

    /**
     * Gets list of closed trades without pnl/gbp calculated.
     *
     * @param view View
     * @return List of trades
     */
    Collection<ClosedTrade> getClosedTradesWithoutPnl(View view);

    /**
     * Gets corresponding live trade by contract number. If no such found, returns null.
     *
     * @param closedTrade Closed trade
     * @param view        View
     * @return Live trade
     */
    @Nullable
    LiveTrade findCorrespondingLiveTrade(ClosedTrade closedTrade, View view);
}