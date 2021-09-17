package com.gcs.gcsplatform.web.components.brokerage;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component(InitialBrokerageBean.NAME)
public class InitialBrokerageBean {

    public static final String NAME = "gcsplatform_InitialBrokerageBean";

    @Inject
    private Metadata metadata;

    /**
     * Enriches brokerage list with specified categories and all of the brokerage types.
     *
     * @param brokerageList Brokerage list which will be enriched
     * @param categories    List of categories
     * @param counterparty  Counterparty that will be assigned to new brokerage
     */
    public void enrichBrokerageWithInitialData(List<CounterpartyBrokerage> brokerageList, List<Category> categories,
            Counterparty counterparty) {
        List<Pair<String, BrokerageType>> pairs = brokerageList.stream()
                .map(brokerage -> new ImmutablePair<>(brokerage.getCategory().getCategory(),
                        brokerage.getBrokerageType()))
                .collect(Collectors.toList());

        for (Category category : categories) {
            for (BrokerageType type : BrokerageType.values()) {
                Pair<String, BrokerageType> pair = new ImmutablePair<>(category.getCategory(), type);
                if (!pairs.contains(pair)) {
                    CounterpartyBrokerage brokerage = metadata.create(CounterpartyBrokerage.class);
                    brokerage.setCounterparty(counterparty);
                    brokerage.setBrokerageType(type);
                    brokerage.setCategory(category);
                    brokerageList.add(brokerage);
                }
            }
        }
    }
}