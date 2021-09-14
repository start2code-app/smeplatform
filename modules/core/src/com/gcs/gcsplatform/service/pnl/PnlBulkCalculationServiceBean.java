package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.FxService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service(PnlBulkCalculationService.NAME)
public class PnlBulkCalculationServiceBean implements PnlBulkCalculationService {

    @Inject
    private FxService fxService;
    @Inject
    private PnlCalculationService pnlCalculationService;

    @Override
    public void bulkCalculatePnl(Collection<? extends Trade> trades) {
        trades.stream()
                .filter(trade -> Objects.nonNull(trade.getInvoiceDate()))
                .filter(trade -> Objects.nonNull(trade.getTradeCurrency()))
                .collect(groupingBy(this::createTradeGroup, toList()))
                .forEach((group, ts) -> {
                    BigDecimal fxValue = fxService.getFxValue(group.getCurrency(), group.getInvoiceDate());
                    for (Trade t : ts) {
                        t.setXrate1(fxValue);
                        updatePnl(t);
                    }
                });
    }

    private TradeGroup createTradeGroup(Trade trade) {
        Date invoiceDate = DateUtils.truncate(trade.getInvoiceDate(), Calendar.MONTH);
        String tradeCurrency = trade.getTradeCurrency();
        return new TradeGroup(invoiceDate, tradeCurrency);
    }

    private void updatePnl(Trade trade) {
        updatePnl(trade, TradeSide.BUY);
        updatePnl(trade, TradeSide.SELL);
    }

    private void updatePnl(Trade trade, TradeSide side) {
        BigDecimal pnl = pnlCalculationService.calculatePnl(trade, side);
        trade.setPnl(pnl, side);
        BigDecimal gbpEquivalent = pnlCalculationService.calculateFxEquivalent(pnl, trade.getXrate1());
        trade.setGbpEquivalent(gbpEquivalent, side);
    }

    private static class TradeGroup {

        private final Date invoiceDate;
        private final String currency;

        public TradeGroup(Date invoiceDate, String currency) {
            this.invoiceDate = invoiceDate;
            this.currency = currency;
        }

        public Date getInvoiceDate() {
            return invoiceDate;
        }

        public String getCurrency() {
            return currency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TradeGroup that = (TradeGroup) o;
            return Objects.equals(invoiceDate, that.invoiceDate) && Objects.equals(currency, that.currency);
        }

        @Override
        public int hashCode() {
            return Objects.hash(invoiceDate, currency);
        }
    }
}