package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isNullOrZero;

@Service(PnlService.NAME)
public class PnlServiceBean implements PnlService {

    @Inject
    private Metadata metadata;

    @Override
    public Collection<Pnl> getPnlByCounterparty(Collection<Trade> trades) {
        Map<PnlGroup, Pnl> pnlMap = new HashMap<>();
        for (Trade trade : trades) {
            sumPnl(pnlMap, trade.getTradeCurrency(), trade.getBuyer(), trade.getBuyPnl(), trade.getBuyGbpEquivalent());
            sumPnl(pnlMap, trade.getTradeCurrency(), trade.getSeller(), trade.getSellPnl(),
                    trade.getSellGbpEquivalent());
        }
        return new ArrayList<>(pnlMap.values());
    }

    @Override
    public Collection<Pnl> getPnlByBroker(Collection<Trade> trades) {
        Map<PnlGroup, Pnl> pnlMap = new HashMap<>();
        for (Trade trade : trades) {
            boolean isBuySplit = Boolean.TRUE.equals(trade.getBuySplit()) && StringUtils.isNotBlank(
                    trade.getBuySplitBroker());
            if (isBuySplit) {
                sumSplitPnl(pnlMap, trade.getTradeCurrency(), trade.getBuybroker(), trade.getBuySplitBroker(),
                        trade.getBuyer(), trade.getBuyPnl(), trade.getBuyGbpEquivalent());
            } else {
                sumPnl(pnlMap, trade.getTradeCurrency(), trade.getBuybroker(), trade.getBuyer(), trade.getBuyPnl(),
                        trade.getBuyGbpEquivalent());
            }

            boolean isSellSplit = Boolean.TRUE.equals(trade.getSellSplit()) && StringUtils.isNotBlank(
                    trade.getSellSplitBroker());
            if (isSellSplit) {
                sumSplitPnl(pnlMap, trade.getTradeCurrency(), trade.getSellbroker(), trade.getSellSplitBroker(),
                        trade.getSeller(), trade.getSellPnl(), trade.getSellGbpEquivalent());
            } else {
                sumPnl(pnlMap, trade.getTradeCurrency(), trade.getSellbroker(), trade.getSeller(),
                        trade.getSellPnl(), trade.getSellGbpEquivalent());
            }
        }
        return new ArrayList<>(pnlMap.values());
    }

    private void sumSplitPnl(Map<PnlGroup, Pnl> pnlMap, String tradeCurrency, String broker, String splitBroker,
            String counterparty, BigDecimal pnlValue, BigDecimal gbpEquivalent) {
        BigDecimal splitPnl = pnlValue.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
        BigDecimal splitGbpEquivalent = gbpEquivalent.divide(BigDecimal.valueOf(2),
                RoundingMode.HALF_EVEN);
        sumPnl(pnlMap, tradeCurrency, broker, counterparty, splitPnl, splitGbpEquivalent);
        sumPnl(pnlMap, tradeCurrency, splitBroker, counterparty, splitPnl, splitGbpEquivalent);
    }

    private void sumPnl(Map<PnlGroup, Pnl> pnlMap, String tradeCurrency, String counterparty,
            BigDecimal pnlValue, BigDecimal gbpEquivalent) {
        sumPnl(pnlMap, tradeCurrency, null, counterparty, pnlValue, gbpEquivalent);
    }

    private void sumPnl(Map<PnlGroup, Pnl> pnlMap, String tradeCurrency, String broker, String counterparty,
            BigDecimal pnlValue, BigDecimal gbpEquivalent) {
        if (isNullOrZero(pnlValue) || isNullOrZero(gbpEquivalent)) {
            return;
        }
        PnlGroup pnlGroup = new PnlGroup(broker, counterparty, tradeCurrency);
        if (pnlMap.containsKey(pnlGroup)) {
            Pnl pnlEntity = pnlMap.get(pnlGroup);

            BigDecimal sumPnl = pnlValue.add(pnlEntity.getPnl());
            pnlEntity.setPnl(sumPnl);

            BigDecimal sumGbpEquivalent = gbpEquivalent.add(pnlEntity.getGbpEquivalent());
            pnlEntity.setGbpEquivalent(sumGbpEquivalent);
        } else {
            Pnl pnlEntity = createPnlEntity(broker, counterparty, tradeCurrency, pnlValue, gbpEquivalent);
            pnlMap.put(pnlGroup, pnlEntity);
        }
    }

    private Pnl createPnlEntity(String broker, String counterparty, String tradeCurrency, BigDecimal pnl,
            BigDecimal gbpEquivalent) {
        Pnl pnlEntity = metadata.create(Pnl.class);
        pnlEntity.setBroker(broker);
        pnlEntity.setCounterparty(counterparty);
        pnlEntity.setTradeCurrency(tradeCurrency);
        pnlEntity.setPnl(pnl);
        pnlEntity.setGbpEquivalent(gbpEquivalent);
        return pnlEntity;
    }

    private static class PnlGroup {

        private final String broker;
        private final String counterparty;
        private final String tradeCurrency;

        public PnlGroup(String broker, String counterparty, String tradeCurrency) {
            this.broker = broker;
            this.counterparty = counterparty;
            this.tradeCurrency = tradeCurrency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PnlGroup pnlGroup = (PnlGroup) o;
            return Objects.equals(broker, pnlGroup.broker) && Objects.equals(counterparty,
                    pnlGroup.counterparty) && Objects.equals(tradeCurrency, pnlGroup.tradeCurrency);
        }

        @Override
        public int hashCode() {
            return Objects.hash(broker, counterparty, tradeCurrency);
        }
    }
}