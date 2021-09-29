package com.gcs.gcsplatform.service;

import java.util.List;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(CurrencyService.NAME)
public class CurrencyServiceBean implements CurrencyService {

    @Inject
    private DataManager dataManager;

    @Override
    public List<Currency> getCurrencies(View view) {
        return dataManager.load(Currency.class)
                .view(view)
                .list();
    }
}