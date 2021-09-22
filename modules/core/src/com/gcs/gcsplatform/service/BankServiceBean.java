package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Bank;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(BankService.NAME)
public class BankServiceBean implements BankService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public Bank findBank(String location, String currency, View view) {
        return dataManager.load(Bank.class)
                .query("select e.bank from gcsplatform_InvoiceBank e "
                        + "where e.location = :location "
                        + "and e.currency.currency = :currency")
                .parameter("location", location)
                .parameter("currency", currency)
                .view(view)
                .optional()
                .orElse(null);
    }
}