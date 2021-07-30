package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;

public interface CounterpartyBrokerageService {

    String NAME = "gcsplatform_CounterpartyBrokerageService";

    /**
     * Searches a counterparty brokerage by counterparty, category, brokerage type.
     * Returns null if no such present.
     *
     * @param counterparty  - Counterparty
     * @param category      - Category
     * @param brokerageType - Brokerage type
     * @return Counterparty brokerage or null
     */
    @Nullable
    CounterpartyBrokerage findCounterpartyBrokerage(Counterparty counterparty, Category category,
            CounterpartyBrokerageType brokerageType);
}