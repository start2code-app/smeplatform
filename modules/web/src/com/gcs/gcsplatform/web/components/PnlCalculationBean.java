package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.HasNumdays;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
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
     * Updates number of days between maturity date and value date.
     *
     * @param hasNumdays Entity
     */
    public void updateNumdays(HasNumdays hasNumdays) {
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
    public void updatePnl(Trade trade) {
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
}
