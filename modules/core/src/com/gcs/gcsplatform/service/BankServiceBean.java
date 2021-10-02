package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.InvoiceBank;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(BankService.NAME)
public class BankServiceBean implements BankService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public InvoiceBank findInvoiceBank(String location, String currency, View view) {
        return dataManager.load(InvoiceBank.class)
                .query("select e from gcsplatform_InvoiceBank e "
                        + "where e.location.name = :location "
                        + "and e.currency.currency = :currency")
                .parameter("location", location)
                .parameter("currency", currency)
                .cacheable(true)
                .view(view)
                .optional()
                .orElse(null);
    }

    @Override
    public boolean bankExists(String location, String currency) {
        return findInvoiceBank(location, currency, new View(InvoiceBank.class, View.MINIMAL)) != null;
    }
}