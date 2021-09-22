package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(CounterpartyService.NAME)
public class CounterpartyServiceBean implements CounterpartyService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public Counterparty findCounterparty(String counterpartyCode, View view) {
        return dataManager.load(Counterparty.class)
                .query("select e from gcsplatform_Counterparty e "
                        + "where e.billingInfo1 = :counterpartyCode")
                .parameter("counterpartyCode", counterpartyCode)
                .view(view)
                .optional()
                .orElse(null);
    }
}