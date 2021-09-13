package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.HasNumdays;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.FxService;
import com.gcs.gcsplatform.service.pnl.PnlCalculationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

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
    @Inject
    private PnlCalculationService pnlCalculationService;

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
     * Updates number of days between maturity date and value date.
     * <p>
     * If maturity date is null, assumes it as today.
     *
     * @param hasNumdays Entity
     */
    public void updateNumdays(HasNumdays hasNumdays) {
        Date maturityDate = hasNumdays.getMaturityDate();
        if (maturityDate == null) {
            maturityDate = new Date();
        }
        hasNumdays.setNumdays(getDaysBetweenDates(hasNumdays.getMaturityDate(), hasNumdays.getValueDate()));
    }

    /**
     * Updates PNL value, its GBP equivalent and FX of specified invoice line.
     *
     * @param invoiceLine Invoice line
     */
    public void updatePnl(InvoiceLine invoiceLine) {
        updateNumdays(invoiceLine);
        BigDecimal fxValue = fxService.getFxValue(invoiceLine.getCurrency(), invoiceLine.getStartDate());
        invoiceLine.setFx(fxValue);
        BigDecimal pnl = pnlCalculationService.calculatePnl(invoiceLine);
        invoiceLine.setPnl(pnl);
        BigDecimal gbpEquivalent = pnlCalculationService.calculateFxEquivalent(pnl, fxValue);
        invoiceLine.setGbpEquivalent(gbpEquivalent);
    }

    /**
     * Updates PNL value and its GBP equivalent of specified trade.
     *
     * @param trade Trade
     */
    public void updatePnl(Trade trade, BigDecimal fxValue) {
        updateNumdays(trade);
        BigDecimal fxValue = fxService.getFxValue(trade.getTradeCurrency(), trade.getInvoiceDate());
        updatePnl(trade, TradeSide.BUY, fxValue);
        updatePnl(trade, TradeSide.SELL, fxValue);
    }

    private void updatePnl(Trade trade, TradeSide side, BigDecimal fxValue) {
        BigDecimal pnl = pnlCalculationService.calculatePnl(trade, side);
        trade.setPnl(pnl, side);
        BigDecimal gbpEquivalent = pnlCalculationService.calculateFxEquivalent(pnl, fxValue);
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
