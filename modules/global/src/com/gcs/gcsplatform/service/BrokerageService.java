package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;

public interface BrokerageService {

    String NAME = "gcsplatform_BrokerageService";

    /**
     * Searches for counterparty brokerage value by counterparty, category, brokerage type.
     * Returns null if no such present or any of arguments is null.
     *
     * @param counterparty  Counterparty name
     * @param category      Category name
     * @param brokerageType Brokerage type
     * @return Counterparty brokerage or null
     */
    @Nullable
    BigDecimal findBrokerageValue(String counterparty, String category, CounterpartyBrokerageType brokerageType);
}