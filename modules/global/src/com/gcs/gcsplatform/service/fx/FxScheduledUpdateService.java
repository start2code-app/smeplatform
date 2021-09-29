package com.gcs.gcsplatform.service.fx;

public interface FxScheduledUpdateService {

    String NAME = "gcsplatform_FxScheduledUpdateService";

    /**
     * Updates FX for all currencies.
     *
     * Method is being used in scheduled task.
     */
    @SuppressWarnings("unused")
    void updateFxForAllCurrencies();
}