package com.gcs.gcsplatform.service.pnl;

import java.util.Collection;

import com.gcs.gcsplatform.entity.pnl.chart.BrokerageTypeCount;
import com.gcs.gcsplatform.entity.pnl.chart.CategoryCount;
import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.pnl.chart.TotalPnl;
import com.gcs.gcsplatform.entity.trade.Trade;

public interface PnlChartService {

    String NAME = "gcsplatform_PnlChartService";

    /**
     * Calculates total PNL by month in GBP equivalent.
     *
     * @param trades Trades
     * @return Total PNL
     */
    Collection<TotalPnl> getTotalPnlByMonth(Collection<? extends Trade> trades);

    /**
     * Calculates total PNL by broker in GBP equivalent.
     *
     * @param pnls Collection of PNL
     * @return Totals PNL by broker
     */
    Collection<TotalPnl> getTotalPnlByBroker(Collection<Pnl> pnls);

    /**
     * Calculates trade count of each category.
     *
     * @param trades Trades
     * @return Category count
     */
    Collection<CategoryCount> getCategoryCount(Collection<? extends Trade> trades);

    /**
     * Calculates trade count of GC and Special brokerage types.
     *
     * @param trades Trades
     * @return Brokerage type count
     */
    Collection<BrokerageTypeCount> getBrokerageTypeCount(Collection<? extends Trade> trades);
}