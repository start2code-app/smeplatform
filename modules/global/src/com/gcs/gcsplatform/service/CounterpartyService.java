package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.View;

public interface CounterpartyService {

    String NAME = "gcsplatform_CounterpartyService";

    /**
     * Searches for a counterparty by specified code.
     *
     * @param counterpartyCode Counterparty code
     * @param view             View
     * @return Counterparty or null
     */
    @Nullable
    Counterparty findCounterparty(String counterpartyCode, View view);
}