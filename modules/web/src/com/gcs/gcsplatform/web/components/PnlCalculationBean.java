package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.FxService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isNullOrZero;
import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getYearMonth;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component(PnlCalculationBean.NAME)
public class PnlCalculationBean {

    public static final String NAME = "gcsplatform_PnlCalculationBean";

    @Inject
    private FxService fxService;

    public void recalculatePnl(Collection<? extends Trade> trades) {
        Map<TradeGroup, ? extends List<? extends Trade>> tradeGroupMap = trades.stream()
                .filter(trade -> Objects.nonNull(trade.getInvoiceDate()))
                .collect(groupingBy(this::createTradeGroup, toList()));
        tradeGroupMap.forEach((group, ts) -> {
                    BigDecimal fxValue = fxService.getFxValue(group.getCurrency(), group.getInvoiceDate());
                    for (Trade t : ts) {
                        updatePnl(t, fxValue);
                    }
                });
    }

    private TradeGroup createTradeGroup(Trade trade) {
        Date invoiceDate = DateUtils.truncate(trade.getInvoiceDate(), Calendar.MONTH);
        String tradeCurrency = trade.getTradeCurrency();
        return new TradeGroup(invoiceDate, tradeCurrency);
    }

    /**
     * Updates number of days between trade's maturity date and value date.
     * If maturity date is null, assumes it as today.
     *
     * @param trade Trade
     */
    public void updateNumdays(Trade trade) {
        Date maturityDate = trade.getMaturityDate();
        if (maturityDate == null) {
            maturityDate = new Date();
        }
        trade.setNumdays(getDaysBetweenDates(maturityDate, trade.getValueDate()));
    }

    /**
     * Updates PNL values and theirs GBP equivalent.
     *
     * @param trade Trade
     */
    public void updatePnl(Trade trade, BigDecimal fxValue) {
        updateNumdays(trade);

        BigDecimal buyPnl = calculatePnl(trade, trade.getBuybrokerage(), Boolean.TRUE.equals(trade.getBuyerCash()));
        trade.setBuyPnl(buyPnl);

        BigDecimal buyGbpEquivalent = calculateGbpEquivalent(buyPnl, fxValue);
        trade.setBuyGbpEquivalent(buyGbpEquivalent);

        BigDecimal sellPnl = calculatePnl(trade, trade.getSellbrokerage(), Boolean.TRUE.equals(trade.getSellerCash()));
        trade.setSellPnl(sellPnl);

        BigDecimal sellGbpEquivalent = calculateGbpEquivalent(sellPnl, fxValue);
        trade.setSellGbpEquivalent(sellGbpEquivalent);
    }

    private BigDecimal calculateGbpEquivalent(BigDecimal amount, BigDecimal fxValue) {
        if (isNullOrZero(fxValue)) {
            return BigDecimal.ZERO;
        }
        return amount.divide(fxValue, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculatePnl(Trade trade, BigDecimal brokerage, boolean cash) {
        BigDecimal numdays = trade.getNumdays() != null ? BigDecimal.valueOf(trade.getNumdays()) : null;
        BigDecimal nominal = trade.getNominal();
        BigDecimal startPrice = trade.getStartPrice();

        if (isNullOrZero(numdays) || isNullOrZero(nominal) || isNullOrZero(brokerage)) {
            return BigDecimal.ZERO;
        }

        BigDecimal xrate2 = isNullOrZero(trade.getXrate2()) ? BigDecimal.valueOf(1) : trade.getXrate2();
        if (cash) {
            if (isNullOrZero(startPrice)) {
                return BigDecimal.ZERO;
            }
            return nominal
                    .multiply(xrate2)
                    .multiply(brokerage)
                    .multiply(numdays)
                    .multiply(startPrice)
                    .multiply(BigDecimal.TEN)
                    .divide(BigDecimal.valueOf(36), RoundingMode.HALF_EVEN);
        } else {
            return nominal
                    .multiply(xrate2)
                    .multiply(brokerage)
                    .multiply(numdays)
                    .multiply(BigDecimal.valueOf(1000))
                    .divide(BigDecimal.valueOf(36), RoundingMode.HALF_EVEN);
        }
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
