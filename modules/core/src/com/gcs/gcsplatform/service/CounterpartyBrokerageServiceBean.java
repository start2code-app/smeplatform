package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

@Service(CounterpartyBrokerageService.NAME)
public class CounterpartyBrokerageServiceBean implements CounterpartyBrokerageService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public CounterpartyBrokerage findCounterpartyBrokerage(Counterparty counterparty, Category category,
            CounterpartyBrokerageType brokerageType) {
        return dataManager.load(CounterpartyBrokerage.class)
                .query("select e from gcsplatform_CounterpartyBrokerage e "
                        + "where e.counterparty = :counterparty "
                        + "and e.category = :category "
                        + "and e.brokerageType = :brokerageType")
                .parameter("counterparty", counterparty)
                .parameter("category", category)
                .parameter("brokerageType", brokerageType)
                .optional()
                .orElse(null);
    }
}