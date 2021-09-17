package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.pnl.chart.BrokerageTypeCount;
import com.gcs.gcsplatform.entity.pnl.chart.CategoryCount;
import com.gcs.gcsplatform.entity.pnl.chart.TotalPnl;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.entity.masterdata.BrokerageType.GC;
import static com.gcs.gcsplatform.entity.masterdata.BrokerageType.SPECIAL;
import static com.gcs.gcsplatform.util.DateUtils.getYearMonth;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

@Service(PnlChartService.NAME)
public class PnlChartServiceBean implements PnlChartService {

    @Inject
    private Metadata metadata;

    @Override
    public Collection<TotalPnl> getTotalPnlByMonth(Collection<? extends Trade> trades) {
        return trades.stream()
                .filter(trade -> Objects.nonNull(trade.getInvoiceDate()))
                .collect(groupingBy(trade -> getYearMonth(trade.getInvoiceDate()), toList()))
                .entrySet().stream()
                .sorted(comparingByKey())
                .map(entry -> {
                    TotalPnl totalPnl = metadata.create(TotalPnl.class);
                    totalPnl.setDescription(entry.getKey());
                    BigDecimal sumPnl = entry.getValue().stream()
                            .filter(trade -> Objects.nonNull(trade.getBuyGbpEquivalent()))
                            .filter(trade -> Objects.nonNull(trade.getSellGbpEquivalent()))
                            .map(trade -> trade.getBuyGbpEquivalent().add(trade.getSellGbpEquivalent()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(2, RoundingMode.HALF_EVEN);
                    totalPnl.setTotal(sumPnl);
                    return totalPnl;
                })
                .collect(toList());
    }


    @Override
    public Collection<TotalPnl> getTotalPnlByBroker(Collection<Pnl> pnls) {
        Map<String, BigDecimal> totalPnlByBrokerMap = pnls.stream()
                .filter(pnl -> Objects.nonNull(pnl.getBroker()))
                .collect(groupingBy(Pnl::getBroker, reducing(BigDecimal.ZERO,
                        Pnl::getGbpEquivalent,
                        BigDecimal::add)));
        return totalPnlByBrokerMap.entrySet().stream()
                .map(entry -> {
                    TotalPnl totalPnl = metadata.create(TotalPnl.class);
                    totalPnl.setDescription(entry.getKey());
                    totalPnl.setTotal(entry.getValue().setScale(2, RoundingMode.HALF_EVEN));
                    return totalPnl;
                })
                .collect(toList());
    }

    @Override
    public Collection<CategoryCount> getCategoryCount(Collection<? extends Trade> trades) {
        Map<String, Long> categoryCountMap = trades.stream()
                .filter(trade -> Objects.nonNull(trade.getCategory()))
                .collect(groupingBy(Trade::getCategory, counting()));
        return categoryCountMap.entrySet().stream()
                .map(entry -> {
                    CategoryCount categoryCount = metadata.create(CategoryCount.class);
                    categoryCount.setCategory(entry.getKey());
                    categoryCount.setCount(entry.getValue());
                    return categoryCount;
                })
                .collect(toList());
    }

    @Override
    public Collection<BrokerageTypeCount> getBrokerageTypeCount(Collection<? extends Trade> trades) {
        Map<BrokerageType, Long> brokerageTypeCountMap = trades.stream()
                .filter(trade -> trade.getBrokerageType() == GC || trade.getBrokerageType() == SPECIAL)
                .collect(groupingBy(Trade::getBrokerageType, counting()));
        return brokerageTypeCountMap.entrySet().stream()
                .map(entry -> {
                    BrokerageTypeCount brokerageTypeCount = metadata.create(BrokerageTypeCount.class);
                    brokerageTypeCount.setBrokerageType(entry.getKey());
                    brokerageTypeCount.setCount(entry.getValue());
                    return brokerageTypeCount;
                })
                .collect(toList());
    }
}