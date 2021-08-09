package com.gcs.gcsplatform.service;

import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;

public interface TradeService {

    String NAME = "gcsplatform_TradeService";

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, tradeCurrency.
     *
     * @param tradeClass - Trade class
     * @param startDate  - Trade update date from
     * @param endDate    - Trade update date to
     * @return List of trades
     */
    Collection<Trade> getEnrichedTradesForPnlChart(Class<? extends TradeContainer> tradeClass, @Nullable Date startDate,
            @Nullable Date endDate);

    /**
     * Gets list of trades enriched with fields required to build PNL chart.
     * <p>
     * Required fields: buyer, buybroker, seller, sellbroker, tradeCurrency.
     *
     * @param tradeClass - Trade class
     * @return List of trades
     */
    Collection<Trade> getEnrichedTradesForPnlChart(Class<? extends TradeContainer> tradeClass);
}