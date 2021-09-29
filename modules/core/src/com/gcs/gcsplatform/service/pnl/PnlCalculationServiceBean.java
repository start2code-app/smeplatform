package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.fx.FxCalculationService;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isAnyNullOrZero;
import static com.gcs.gcsplatform.util.BigDecimalUtils.isNotNullOrZero;
import static com.gcs.gcsplatform.util.BigDecimalUtils.isNullOrZero;

@Service(PnlCalculationService.NAME)
public class PnlCalculationServiceBean implements PnlCalculationService {

    @Inject
    private TradeConfig tradeConfig;
    @Inject
    private FxCalculationService fxCalculationService;

    @Override
    public BigDecimal calculatePnl(Trade trade, TradeSide side) {
        BigDecimal pnl = calculatePnl(trade.getNumdays(), trade.getNominal(), trade.getBrokerage(side), trade.getXrate(),
                trade.getStartPrice(), trade.getCash(side));
        if (isNotNullOrZero(pnl) && trade.getCommissionOverride(side)) {
            BigDecimal usdMinimalCommission = tradeConfig.getUsdMinimalCommission();
            BigDecimal usdEquivalent = fxCalculationService.calculateEquivalent(pnl, trade.getFx(), trade.getFxUsd());
            if (usdEquivalent.compareTo(usdMinimalCommission) < 0) {
                return fxCalculationService.calculateEquivalent(usdMinimalCommission, trade.getFxUsd(), trade.getFx());
            }
        }
        return pnl;
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
                    .divide(BigDecimal.valueOf(36), 2, RoundingMode.HALF_UP);
        } else {
            return nominal
                    .multiply(xrateOrDefault)
                    .multiply(brokerage)
                    .multiply(numdaysDecimal)
                    .multiply(BigDecimal.valueOf(1000))
                    .divide(BigDecimal.valueOf(36), 2, RoundingMode.HALF_UP);
        }
    }
}