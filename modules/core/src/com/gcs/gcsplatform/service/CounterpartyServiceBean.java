package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

@Service(CounterpartyService.NAME)
public class CounterpartyServiceBean implements CounterpartyService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public Counterparty findCounterparty(String name) {
        return dataManager.load(Counterparty.class)
                .query("select e from gcsplatform_Counterparty e "
                        + "where e.counterparty = :name")
                .parameter("name", name)
                .optional()
                .orElse(null);
    }
}