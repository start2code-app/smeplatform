package com.gcs.gcsplatform.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
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
    public <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view,
            @Nullable Date startDate, @Nullable Date endDate) {
        Preconditions.checkNotNullArgument(tradeClass, "Trade class can't be null");
        String tradeEntity = metadataTools.getEntityName(tradeClass);
        FluentLoader.ByQuery<T, UUID> query = dataManager.load(tradeClass)
                .query("select e from " + tradeEntity + " e "
                        + "where e.buyer is not null "
                        + "and e.buybroker is not null "
                        + "and e.tradeCurrency is not null "
                        + "and e.seller is not null "
                        + "and e.sellbroker is not null "
                        + "and e.category is not null")
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

        query.condition(logicalCondition);

        return query.list();
    }

    @Override
    public <T extends Trade> Collection<T> getEnrichedTradesForPnlChart(Class<T> tradeClass, View view) {
        return getEnrichedTradesForPnlChart(tradeClass, view, null, null);
    }

    @Nullable
    @Override
    public LiveTrade getCorrespondingLiveTrade(ClosedTrade closedTrade, View view) {
        return dataManager.load(LiveTrade.class)
                .query("select e from gcsplatform_LiveTrade e "
                        + "where e.traderef = :traderef")
                .parameter("traderef", closedTrade.getTraderef())
                .view(view)
                .optional()
                .orElse(null);
    }
}