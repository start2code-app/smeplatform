package com.gcs.gcsplatform.service.pnl;

import java.util.Date;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;

public interface ClosedTradePnlUpdateService {

    String NAME = "gcsplatform_ClosedTradePnlUpdateService";

    /**
     * Updates pnl/gbp equivalent and FX for all closed trades.
     * <p>
     * Method is being used in scheduled task.
     */
    @SuppressWarnings("unused")
    void updatePnl();

    /**
     * Calculates pnl/gbp equivalent and FX for closed trades of specified month and currency.
     *
     * @param currency    Trade currency
     * @param billingDate FX billing month
     */
    void updatePnl(String currency, Date billingDate);

    /**
     * Calculates pnl/gbp equivalent and FX for closed trades of specified counterparty.
     * <p>
     * Updates only trades within current month.
     *
     * @param counterparty Counterparty
     */
    void updatePnl(Counterparty counterparty);
}