package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.FxService;
import com.gcs.gcsplatform.service.pnl.PnlCalculationService;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;

@Component(PnlCalculationBean.NAME)
public class PnlCalculationBean {

    public static final String NAME = "gcsplatform_PnlCalculationBean";

    @Inject
    private FxService fxService;
    @Inject
    private PnlCalculationService pnlCalculationService;

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
        BigDecimal pnl = pnlCalculationService.calculatePnl(trade.getNumdays(), trade.getNominal(),
                trade.getBrokerage(side), trade.getXrate2(), trade.getStartPrice(), trade.getCash(side));
        trade.setPnl(pnl, side);
        BigDecimal gbpEquivalent = pnlCalculationService.calculateFxEquivalent(pnl, fxValue);
        trade.setGbpEquivalent(gbpEquivalent, side);
    }
}
