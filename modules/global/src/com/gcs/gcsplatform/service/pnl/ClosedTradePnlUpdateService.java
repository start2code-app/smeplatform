package com.gcs.gcsplatform.service.pnl;

import java.util.Date;

public interface ClosedTradePnlUpdateService {

    String NAME = "gcsplatform_ClosedTradePnlUpdateService";

    /**
     * Updates pnl/gbp equivalent and FX for closed trades where pnl/gbp equivalent is null.
     *
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
}