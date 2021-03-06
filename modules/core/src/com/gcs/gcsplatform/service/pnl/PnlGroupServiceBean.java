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
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isNullOrZero;

@Service(PnlGroupService.NAME)
public class PnlGroupServiceBean implements PnlGroupService {

    @Inject
    private Metadata metadata;

    @Override
    public Collection<Pnl> getPnlByCounterparty(Collection<? extends Trade> trades) {
        Map<PnlGroup, Pnl> pnlMap = new HashMap<>();
        for (Trade trade : trades) {
            sumPnlByCounterparty(pnlMap, trade, TradeSide.BUY);
            sumPnlByCounterparty(pnlMap, trade, TradeSide.SELL);
        }
        return new ArrayList<>(pnlMap.values());
    }

    private void sumPnlByCounterparty(Map<PnlGroup, Pnl> pnlMap, Trade trade, TradeSide side) {
        if (trade.getPutOnInvoice(side)) {
            sumPnl(pnlMap, trade.getCurrency(), null, trade.getCounterparty(side), trade.getPnl(side),
                    trade.getGbpEquivalent(side));
        }
    }

    @Override
    public Collection<Pnl> getPnlByBroker(Collection<? extends Trade> trades) {
        Map<PnlGroup, Pnl> pnlMap = new HashMap<>();
        for (Trade trade : trades) {
            sumPnlByBroker(pnlMap, trade, TradeSide.BUY);
            sumPnlByBroker(pnlMap, trade, TradeSide.SELL);
        }
        return new ArrayList<>(pnlMap.values());
    }

    private void sumPnlByBroker(Map<PnlGroup, Pnl> pnlMap, Trade trade, TradeSide side) {
        boolean isSplit = trade.getSplit(side) && StringUtils.isNotBlank(trade.getSplitBroker(side));
        if (isSplit) {
            sumSplitPnl(pnlMap, trade.getCurrency(), trade.getBroker(side), trade.getSplitBroker(side),
                    trade.getCounterparty(side), trade.getPnl(side), trade.getGbpEquivalent(side));
        } else {
            sumPnl(pnlMap, trade.getCurrency(), trade.getBroker(side), trade.getCounterparty(side),
                    trade.getPnl(side), trade.getGbpEquivalent(side));
        }
    }

    private void sumSplitPnl(Map<PnlGroup, Pnl> pnlMap, String currency, String broker, String splitBroker,
            String counterparty, BigDecimal pnlValue, BigDecimal gbpEquivalent) {
        BigDecimal splitPnl = pnlValue.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
        BigDecimal splitGbpEquivalent = gbpEquivalent.divide(BigDecimal.valueOf(2),
                RoundingMode.HALF_EVEN);
        sumPnl(pnlMap, currency, broker, counterparty, splitPnl, splitGbpEquivalent);
        sumPnl(pnlMap, currency, splitBroker, counterparty, splitPnl, splitGbpEquivalent);
    }

    private void sumPnl(Map<PnlGroup, Pnl> pnlMap, String currency, String broker, String counterparty,
            BigDecimal pnlValue, BigDecimal gbpEquivalent) {
        if (isNullOrZero(pnlValue) || isNullOrZero(gbpEquivalent)) {
            return;
        }
        PnlGroup pnlGroup = new PnlGroup(broker, counterparty, currency);
        if (pnlMap.containsKey(pnlGroup)) {
            Pnl pnlEntity = pnlMap.get(pnlGroup);

            BigDecimal sumPnl = pnlValue.add(pnlEntity.getPnl());
            pnlEntity.setPnl(sumPnl);

            BigDecimal sumGbpEquivalent = gbpEquivalent.add(pnlEntity.getGbpEquivalent());
            pnlEntity.setGbpEquivalent(sumGbpEquivalent);
        } else {
            Pnl pnlEntity = createPnlEntity(broker, counterparty, currency, pnlValue, gbpEquivalent);
            pnlMap.put(pnlGroup, pnlEntity);
        }
    }

    private Pnl createPnlEntity(String broker, String counterparty, String currency, BigDecimal pnl,
            BigDecimal gbpEquivalent) {
        Pnl pnlEntity = metadata.create(Pnl.class);
        pnlEntity.setBroker(broker);
        pnlEntity.setCounterparty(counterparty);
        pnlEntity.setCurrency(currency);
        pnlEntity.setPnl(pnl);
        pnlEntity.setGbpEquivalent(gbpEquivalent);
        return pnlEntity;
    }

    private static class PnlGroup {

        private final String broker;
        private final String counterparty;
        private final String currency;

        public PnlGroup(String broker, String counterparty, String currency) {
            this.broker = broker;
            this.counterparty = counterparty;
            this.currency = currency;
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
                    pnlGroup.counterparty) && Objects.equals(currency, pnlGroup.currency);
        }

        @Override
        public int hashCode() {
            return Objects.hash(broker, counterparty, currency);
        }
    }
}