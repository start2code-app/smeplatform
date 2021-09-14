package com.gcs.gcsplatform.service.pnl;

import java.util.Collection;

import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.trade.Trade;

public interface PnlGroupService {

    String NAME = "gcsplatform_PnlGroupService";

    /**
     * Groups and sums PNL by counterparty, currency.
     *
     * @param trades Trades
     * @return List of grouped PNL
     */
    Collection<Pnl> getPnlByCounterparty(Collection<? extends Trade> trades);

    /**
     * Groups and sums PNL by broker, counterparty, currency.
     *
     * @param trades Trades
     * @return List of grouped PNL
     */
    Collection<Pnl> getPnlByBroker(Collection<? extends Trade> trades);
}