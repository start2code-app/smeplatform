package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.FxService;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isNullOrZero;
import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;

@Component(PnlCalculationBean.NAME)
public class PnlCalculationBean {

    public static final String NAME = "gcsplatform_PnlCalculationBean";

    @Inject
    private FxService fxService;

    /**
     * Updates number of days between trade's maturity date and value date.
     *
     * @param trade Trade
     */
    public void updateNumdays(Trade trade) {
        trade.setNumdays(getDaysBetweenDates(trade.getMaturityDate(), trade.getValueDate()));
    }

    /**
     * Updates PNL values and theirs GBP equivalent.
     *
     * @param trade Trade
     */
    public void updatePnl(Trade trade) {
        updateNumdays(trade);
        BigDecimal fxValue = fxService.getFxValue(trade.getTradeCurrency(), trade.getInvoiceDate());
        updatePnl(trade, TradeSide.BUY, fxValue);
        updatePnl(trade, TradeSide.SELL, fxValue);
    }

    private void updatePnl(Trade trade, TradeSide side, BigDecimal fxValue) {
        BigDecimal numdays = trade.getNumdays() != null ? BigDecimal.valueOf(trade.getNumdays()) : null;
        BigDecimal nominal = trade.getNominal();
        BigDecimal brokerage = trade.getBrokerage(side);
        BigDecimal xrate = isNullOrZero(trade.getXrate2()) ? BigDecimal.valueOf(1) : trade.getXrate2();
        BigDecimal startPrice = trade.getStartPrice();
        boolean cash = Boolean.TRUE.equals(trade.getCash(side));

        BigDecimal pnl;
        if (isNullOrZero(numdays) || isNullOrZero(nominal) || isNullOrZero(brokerage) || (cash && isNullOrZero(
                startPrice))) {
            pnl = BigDecimal.ZERO;
        } else {
            pnl = calculatePnl(numdays, nominal, brokerage, xrate, startPrice, cash);
        }
        trade.setPnl(pnl, side);
        BigDecimal gbpEquivalent = calculateGbpEquivalent(pnl, fxValue);
        trade.setGbpEquivalent(gbpEquivalent, side);
    }

    private BigDecimal calculatePnl(BigDecimal numdays, BigDecimal nominal, BigDecimal brokerage, BigDecimal xrate,
            BigDecimal startPrice, boolean cash) {
        if (cash) {
            return nominal
                    .multiply(xrate)
                    .multiply(brokerage)
                    .multiply(numdays)
                    .multiply(startPrice)
                    .multiply(BigDecimal.TEN)
                    .divide(BigDecimal.valueOf(36), RoundingMode.HALF_EVEN);
        } else {
            return nominal
                    .multiply(xrate)
                    .multiply(brokerage)
                    .multiply(numdays)
                    .multiply(BigDecimal.valueOf(1000))
                    .divide(BigDecimal.valueOf(36), RoundingMode.HALF_EVEN);
        }
    }

    private BigDecimal calculateGbpEquivalent(BigDecimal amount, BigDecimal fxValue) {
        if (isNullOrZero(fxValue)) {
            return BigDecimal.ZERO;
        }
        return amount.divide(fxValue, RoundingMode.HALF_EVEN);
    }
}
