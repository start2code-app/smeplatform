package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;

public interface CounterpartyService {

    String NAME = "gcsplatform_CounterpartyService";

    /**
     * Searches a counterparty by name.
     * Returns null if no such present.
     *
     * @param name - Counterparty name
     * @return Counterparty or null
     */
    @Nullable
    Counterparty findCounterparty(String name);
}