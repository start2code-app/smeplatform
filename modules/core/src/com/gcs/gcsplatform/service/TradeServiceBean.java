package com.gcs.gcsplatform.service;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
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
    public Collection<Trade> getEnrichedTradesForPnlChart(Class<? extends TradeContainer> tradeClass,
            @Nullable Date startDate, @Nullable Date endDate) {
        Preconditions.checkNotNullArgument(tradeClass, "Trade class can't be null");
        String tradeEntity = metadataTools.getEntityName(tradeClass);
        FluentLoader.ByQuery<? extends TradeContainer, UUID> query = dataManager.load(tradeClass)
                .query("select e from " + tradeEntity + " e "
                        + "where e.trade.buyer is not null "
                        + "and e.trade.buybroker is not null "
                        + "and e.trade.tradeCurrency is not null "
                        + "and e.trade.seller is not null "
                        + "and e.trade.sellbroker is not null")
                .view(viewBuilder -> viewBuilder.add("trade", View.LOCAL));

        LogicalCondition logicalCondition = LogicalCondition.and();

        if (startDate != null) {
            logicalCondition.add(new JpqlCondition("e.updateTs >= :startDate"));
            query.parameter("startDate", startDate, TemporalType.DATE);
        }

        if (endDate != null) {
            logicalCondition.add(new JpqlCondition("e.updateTs <= :endDate"));
            query.parameter("endDate", endDate, TemporalType.DATE);
        }

        query.condition(logicalCondition);

        return query.list().stream()
                .map(TradeContainer::getTrade)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trade> getEnrichedTradesForPnlChart(Class<? extends TradeContainer> tradeClass) {
        return getEnrichedTradesForPnlChart(tradeClass, null, null);
    }
}