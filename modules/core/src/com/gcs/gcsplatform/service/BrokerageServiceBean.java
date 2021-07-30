package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

@Service(BrokerageService.NAME)
public class BrokerageServiceBean implements BrokerageService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public BigDecimal findBrokerageValue(String counterparty, String category,
            CounterpartyBrokerageType brokerageType) {
        return dataManager.loadValue("select e.brokerageValue from gcsplatform_CounterpartyBrokerage e "
                + "where e.counterparty.counterparty = :counterparty "
                + "and e.category.category = :category "
                + "and e.brokerageType = :brokerageType", BigDecimal.class)
                .parameter("counterparty", counterparty)
                .parameter("category", category)
                .parameter("brokerageType", brokerageType)
                .optional()
                .orElse(null);
    }
}