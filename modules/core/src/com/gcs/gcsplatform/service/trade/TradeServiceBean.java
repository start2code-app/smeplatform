package com.gcs.gcsplatform.service.trade;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.queryconditions.Condition;
import com.haulmont.cuba.core.global.queryconditions.JpqlCondition;
import com.haulmont.cuba.core.global.queryconditions.LogicalCondition;
import org.springframework.stereotype.Service;

@Service(TradeService.NAME)
public class TradeServiceBean implements TradeService {

    @Inject
    private DataManager dataManager;
    @Inject
    private MetadataTools metadataTools;

    @Override
    public <T extends Trade> Collection<T> getTrades(Class<T> tradeClass, @Nullable Date startDate,
            @Nullable Date endDate, View view) {
        return getTrades(tradeClass, startDate, endDate, null, view)
                .list();
    }

    @Override
    public <T extends Trade> Collection<T> getTrades(Class<T> tradeClass, View view) {
        return getTrades(tradeClass, null, null, view);
    }

    @Override
    public <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, @Nullable Date startDate,
            @Nullable Date endDate, View view) {
        LogicalCondition condition = LogicalCondition.and();

        condition.add(new JpqlCondition("e.buyer is not null"));
        condition.add(new JpqlCondition("e.buybroker is not null"));
        condition.add(new JpqlCondition("e.repoCurrency is not null"));
        condition.add(new JpqlCondition("e.bondCurrency is not null"));
        condition.add(new JpqlCondition("e.seller is not null"));
        condition.add(new JpqlCondition("e.sellbroker is not null"));
        condition.add(new JpqlCondition("e.category is not null"));

        return getTrades(tradeClass, startDate, endDate, condition, view)
                .list();
    }

    @Override
    public <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view) {
        return getEnrichedTradesForPnlChart(tradeClass, null, null, view);
    }

    @Override
    public <T extends Trade> Collection<T> getTradesByCurrency(Class<T> tradeClass, String currency,
            @Nullable Date startDate, @Nullable Date endDate, View view) {
        LogicalCondition condition = LogicalCondition.or();

        condition.add(new JpqlCondition("e.repoCurrency = :currency"));
        condition.add(new JpqlCondition("e.bondCurrency = :currency"));

        return getTrades(tradeClass, startDate, endDate, condition, view)
                .parameter("currency", currency)
                .list();
    }

    @Override
    public <T extends Trade> Collection<T> getTradesByCounterparty(Class<T> tradeClass, String counterparty,
            @Nullable Date startDate, @Nullable Date endDate, View view) {
        LogicalCondition condition = LogicalCondition.or();

        condition.add(new JpqlCondition("e.buyer = :counterparty"));
        condition.add(new JpqlCondition("e.seller = :counterparty"));

        return getTrades(tradeClass, startDate, endDate, condition, view)
                .parameter("counterparty", counterparty)
                .list();
    }

    @Nullable
    @Override
    public <T extends Trade> T findTrade(Class<T> tradeClass, String traderef, View view) {
        String tradeEntity = metadataTools.getEntityName(tradeClass);
        return dataManager.load(tradeClass)
                .query("select e from " + tradeEntity + " e "
                        + "where e.traderef = :traderef")
                .parameter("traderef", traderef)
                .view(view)
                .optional()
                .orElse(null);
    }

    private <T extends Trade> FluentLoader.ByQuery<T, UUID> getTrades(Class<T> tradeClass, @Nullable Date startDate,
            @Nullable Date endDate, @Nullable Condition condition, View view) {
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