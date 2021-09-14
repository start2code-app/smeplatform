package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.pnl.PnlCalculationService;
import org.springframework.stereotype.Component;

@Component(PnlCalculationBean.NAME)
public class PnlCalculationBean {

    public static final String NAME = "gcsplatform_PnlCalculationBean";

    @Inject
    private PnlCalculationService pnlCalculationService;

    /**
     * Updates PNL value, its GBP equivalent and FX of specified invoice line.
     *
     * @param invoiceLine Invoice line
     */
    public void updatePnl(InvoiceLine invoiceLine) {
        BigDecimal pnl = pnlCalculationService.calculatePnl(invoiceLine);
        invoiceLine.setPnl(pnl);
        BigDecimal gbpEquivalent = pnlCalculationService.calculateFxEquivalent(pnl, invoiceLine.getFx());
        invoiceLine.setGbpEquivalent(gbpEquivalent);
    }

    /**
     * Updates PNL value and its GBP equivalent and FX of specified trade.
     *
     * @param trade Trade
     */
    public void updatePnl(Trade trade) {
        updatePnl(trade, TradeSide.BUY);
        updatePnl(trade, TradeSide.SELL);
    }

    private void updatePnl(Trade trade, TradeSide side) {
        BigDecimal pnl = pnlCalculationService.calculatePnl(trade, side);
        trade.setPnl(pnl, side);
        BigDecimal gbpEquivalent = pnlCalculationService.calculateFxEquivalent(pnl, trade.getXrate1());
        trade.setGbpEquivalent(gbpEquivalent, side);
    }
}
