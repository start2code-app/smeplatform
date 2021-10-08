package com.gcs.gcsplatform.service.pnl;

import java.util.Date;
import javax.annotation.Nullable;

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
     * Calculates pnl/gbp equivalent for closed trades of specified date interval and currency.
     *
     * @param currency  Currency
     * @param startDate Trade date from
     * @param endDate   Trade date to
     */
    void updatePnl(String currency, @Nullable Date startDate, @Nullable Date endDate);

    /**
     * Calculates pnl/gbp equivalent for closed trades of specified date interval and counterparty.
     *
     * @param counterparty Counterparty
     * @param startDate    Trade date from
     * @param endDate      Trade date to
     */
    void updatePnl(Counterparty counterparty, @Nullable Date startDate, @Nullable Date endDate);
}