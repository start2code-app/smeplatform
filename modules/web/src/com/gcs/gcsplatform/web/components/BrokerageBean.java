package com.gcs.gcsplatform.web.components;

import java.math.BigDecimal;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.BrokerageService;
import org.springframework.stereotype.Component;

@Component(BrokerageBean.NAME)
public class BrokerageBean {

    public static final String NAME = "gcsplatform_BrokerageBean";

    @Inject
    private BrokerageService brokerageService;
    @Inject
    private PnlCalculationBean pnlCalculationBean;

    /**
     * Updates buybrokerage and sellbrokerage values based on trade counterparty and category.
     * After changing brokerages, recalculates PNL.
     *
     * @param trade - Trade
     */
    public void updateBrokerage(Trade trade) {
        if (Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs())) {
            return;
        }

        BigDecimal buyBrokerage = brokerageService.findBrokerageValue(trade.getBuyer(), trade.getCategory(),
                trade.getBrokerageType());
        trade.setBuybrokerage(buyBrokerage);

        BigDecimal sellBrokerage = brokerageService.findBrokerageValue(trade.getSeller(), trade.getCategory(),
                trade.getBrokerageType());
        trade.setSellbrokerage(sellBrokerage);

        pnlCalculationBean.updatePnl(trade);
    }
}
