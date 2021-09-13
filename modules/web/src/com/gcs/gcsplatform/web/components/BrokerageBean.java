package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.BrokerageService;
import org.springframework.stereotype.Component;

@Component(BrokerageBean.NAME)
public class BrokerageBean {

    public static final String NAME = "gcsplatform_BrokerageBean";

    @Inject
    private BrokerageService brokerageService;

    /**
     * Updates brokerage of specified invoice line.
     *
     * @param invoiceLine Invoice line
     */
    public void updateBrokerage(InvoiceLine invoiceLine) {
        ClosedTrade trade = invoiceLine.getTrade();
        BigDecimal brokerage = brokerageService.findBrokerageValue(invoiceLine.getCounterparty(), trade.getCategory(),
                trade.getBrokerageType());
        invoiceLine.setBrokerage(brokerage);
    }

    /**
     * Updates buybrokerage and sellbrokerage values based on trade counterparty and category.
     *
     * @param trade Trade
     */
    public void updateBrokerage(Trade trade) {
        if (Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs())) {
            return;
        }
        updateBrokerage(trade, TradeSide.BUY);
        updateBrokerage(trade, TradeSide.SELL);
    }

    private void updateBrokerage(Trade trade, TradeSide side) {
        BigDecimal brokerage = brokerageService.findBrokerageValue(trade.getCounterparty(side), trade.getCategory(),
                trade.getBrokerageType());
        trade.setBrokerage(brokerage, side);
    }
}
