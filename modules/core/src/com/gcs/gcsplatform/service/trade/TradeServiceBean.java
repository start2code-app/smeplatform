package com.gcs.gcsplatform.service.trade;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.queryconditions.Condition;
import com.haulmont.cuba.core.global.queryconditions.JpqlCondition;
import com.haulmont.cuba.core.global.queryconditions.LogicalCondition;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

@Service(TradeService.NAME)
public class TradeServiceBean implements TradeService {

    @Inject
    private DataManager dataManager;
    @Inject
    private MetadataTools metadataTools;

    @Override
    public <T extends Trade> Collection<T> getTrades(Class<T> tradeClass, View view, @Nullable Date startDate,
            @Nullable Date endDate) {
        return getTrades(tradeClass, view, startDate, endDate, null)
                .list();
    }

    @Override
    public <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view,
            @Nullable Date startDate, @Nullable Date endDate) {
        LogicalCondition tradeEnrichedCondition = LogicalCondition.and();

        tradeEnrichedCondition.add(new JpqlCondition("e.buyer is not null"));
        tradeEnrichedCondition.add(new JpqlCondition("e.buybroker is not null"));
        tradeEnrichedCondition.add(new JpqlCondition("e.tradeCurrency is not null"));
        tradeEnrichedCondition.add(new JpqlCondition("e.seller is not null"));
        tradeEnrichedCondition.add(new JpqlCondition("e.sellbroker is not null"));
        tradeEnrichedCondition.add(new JpqlCondition("e.category is not null"));

        return getTrades(tradeClass, view, startDate, endDate, tradeEnrichedCondition)
                .list();
    }

    @Override
    public <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view) {
        return getEnrichedTradesForPnlChart(tradeClass, view, null, null);
    }

    @Override
    public Collection<ClosedTrade> getClosedTradesToUpdateFx(String currency, Date billingDate, View view) {
        LogicalCondition tradeCurrencyCondition = LogicalCondition.and();

        tradeCurrencyCondition.add(new JpqlCondition("e.tradeCurrency = :currency"));

        return getTrades(ClosedTrade.class, view, getFirstDayOfMonth(billingDate), getLastDayOfMonth(billingDate),
                tradeCurrencyCondition)
                .parameter("currency", currency)
                .list();
    }

    @Override
    public Collection<ClosedTrade> getClosedTradesWithoutPnl(View view) {
        return dataManager.load(ClosedTrade.class)
                .query("select e from gcsplatform_ClosedTrade e "
                        + "where e.buyPnl is null "
                        + "or e.sellPnl is null "
                        + "or e.buyGbpEquivalent is null "
                        + "or e.sellGbpEquivalent is null")
                .view(view)
                .list();
    }

    @Nullable
    @Override
    public LiveTrade findCorrespondingLiveTrade(ClosedTrade closedTrade, View view) {
        return dataManager.load(LiveTrade.class)
                .query("select e from gcsplatform_LiveTrade e "
                        + "where e.traderef = :traderef")
                .parameter("traderef", closedTrade.getTraderef())
                .view(view)
                .optional()
                .orElse(null);
    }

    private <T extends Trade> FluentLoader.ByQuery<T, UUID> getTrades(Class<T> tradeClass, View view,
            @Nullable Date startDate, @Nullable Date endDate, Condition condition) {
        String tradeEntity = metadataTools.getEntityName(tradeClass);
        FluentLoader.ByQuery<T, UUID> query = dataManager.load(tradeClass)
                .query("select e from " + tradeEntity + " e ")
                .view(view);

        LogicalCondition logicalCondition = LogicalCondition.and();

        if (startDate != null) {
            logicalCondition.add(new JpqlCondition("e.invoiceDate >= :startDate"));
            query.parameter("startDate", startDate, TemporalType.DATE);
        }

        if (endDate != null) {
            logicalCondition.add(new JpqlCondition("e.invoiceDate <= :endDate"));
            query.parameter("endDate", endDate, TemporalType.DATE);
        }

        if (condition != null) {
            logicalCondition.add(condition);
        }
        query.condition(logicalCondition);

        return query;
    }
}