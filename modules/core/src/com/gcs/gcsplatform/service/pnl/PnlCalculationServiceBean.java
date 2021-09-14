package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isAnyNullOrZero;
import static com.gcs.gcsplatform.util.BigDecimalUtils.isNullOrZero;

@Service(PnlCalculationService.NAME)
public class PnlCalculationServiceBean implements PnlCalculationService {

    @Override
    public BigDecimal calculatePnl(Trade trade, TradeSide side) {
        return calculatePnl(trade.getNumdays(), trade.getNominal(), trade.getBrokerage(side), trade.getXrate2(),
                trade.getStartPrice(), trade.getCash(side));
    }

    @Override
    public BigDecimal calculatePnl(InvoiceLine invoiceLine) {
        return calculatePnl(invoiceLine.getNumdays(), invoiceLine.getNominal(), invoiceLine.getBrokerage(),
                invoiceLine.getXrate(), invoiceLine.getStartPrice(), invoiceLine.getCash());
    }

    private BigDecimal calculatePnl(Long numdays, BigDecimal nominal, BigDecimal brokerage, BigDecimal xrate,
            BigDecimal startPrice, Boolean cash) {
        BigDecimal numdaysDecimal = numdays != null ? BigDecimal.valueOf(numdays) : null;
        boolean isCash = Boolean.TRUE.equals(cash);

        if (isAnyNullOrZero(nominal, brokerage, numdaysDecimal) || isCash && isNullOrZero(startPrice)) {
            return BigDecimal.ZERO;
        }

        BigDecimal xrateOrDefault = isNullOrZero(xrate) ? BigDecimal.valueOf(1) : xrate;
        if (isCash) {
            return nominal
                    .multiply(xrateOrDefault)
                    .multiply(brokerage)
                    .multiply(numdaysDecimal)
                    .multiply(startPrice)
                    .multiply(BigDecimal.TEN)
                    .divide(BigDecimal.valueOf(36), RoundingMode.HALF_EVEN);
        } else {
            return nominal
                    .multiply(xrateOrDefault)
                    .multiply(brokerage)
                    .multiply(numdaysDecimal)
                    .multiply(BigDecimal.valueOf(1000))
                    .divide(BigDecimal.valueOf(36), RoundingMode.HALF_EVEN);
        }
    }

    @Override
    public BigDecimal calculateFxEquivalent(BigDecimal amount, BigDecimal fxValue) {
        if (isAnyNullOrZero(amount, fxValue)) {
            return BigDecimal.ZERO;
        }
        return amount.divide(fxValue, RoundingMode.HALF_EVEN);
    }
}