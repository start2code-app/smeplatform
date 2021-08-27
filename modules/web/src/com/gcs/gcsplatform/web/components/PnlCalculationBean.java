package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
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

        BigDecimal fxValue = fxService.getFxValue(trade.getTradeCurrency(), trade.getTradeDate());

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
}
