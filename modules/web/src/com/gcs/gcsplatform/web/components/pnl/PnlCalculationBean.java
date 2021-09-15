package com.gcs.gcsplatform.web.components.pnl;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.fx.FxService;
import com.gcs.gcsplatform.service.fx.FxCalculationService;
import com.gcs.gcsplatform.service.pnl.PnlCalculationService;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;

@Component(PnlCalculationBean.NAME)
public class PnlCalculationBean {

    public static final String NAME = "gcsplatform_PnlCalculationBean";

    @Inject
    private PnlCalculationService pnlCalculationService;
    @Inject
    private FxService fxService;
    @Inject
    private FxCalculationService fxCalculationService;

    /**
     * Updates numdays, PNL value, its GBP equivalent of specified invoice line.
     *
     * FX is being taken from invoice line itself since it can be manually filled by a user.
     *
     * @param invoiceLine Invoice line
     */
    public void updatePnl(InvoiceLine invoiceLine) {
        invoiceLine.setNumdays(getDaysBetweenDates(invoiceLine.getMaturityDate(), invoiceLine.getValueDate()));
        BigDecimal pnl = pnlCalculationService.calculatePnl(invoiceLine);
        invoiceLine.setPnl(pnl);
        BigDecimal gbpEquivalent = fxCalculationService.calculateFxEquivalent(pnl, invoiceLine.getFx());
        invoiceLine.setGbpEquivalent(gbpEquivalent);
    }

    /**
     * Updates numdays, PNL value and its GBP equivalent and FX of specified trade.
     *
     * @param trade Trade
     */
    public void updatePnl(Trade trade) {
        trade.setNumdays(getDaysBetweenDates(trade.getMaturityDate(), trade.getValueDate()));
        trade.setXrate1(fxService.findFxValue(trade.getTradeCurrency(), trade.getInvoiceDate()));
        updatePnl(trade, TradeSide.BUY);
        updatePnl(trade, TradeSide.SELL);
    }

    private void updatePnl(Trade trade, TradeSide side) {
        BigDecimal pnl = pnlCalculationService.calculatePnl(trade, side);
        trade.setPnl(pnl, side);
        BigDecimal gbpEquivalent = fxCalculationService.calculateFxEquivalent(pnl, trade.getXrate1());
        trade.setGbpEquivalent(gbpEquivalent, side);
    }
}
