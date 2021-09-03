package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;

public interface PnlCalculationService {

    String NAME = "gcsplatform_PnlCalculationService";

    /**
     * Calculates PNL for trade.
     *
     * @param trade Trade
     * @param side  Trade side (buy/sell)
     * @return PNL
     */
    BigDecimal calculatePnl(Trade trade, TradeSide side);

    /**
     * Calculates PNL for invoice line.
     *
     * @param invoiceLine Invoice line
     * @return PNL
     */
    BigDecimal calculatePnl(InvoiceLine invoiceLine);

    /**
     * Calculates FX amount equivalent. Returns zero if any of arguments is zero.
     *
     * @param amount  Amount
     * @param fxValue Foreign exchange value
     * @return FX amount equivalent
     */
    BigDecimal calculateFxEquivalent(BigDecimal amount, BigDecimal fxValue);
}