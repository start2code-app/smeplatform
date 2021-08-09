package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;

@Service(FxService.NAME)
public class FxServiceBean implements FxService {

    @Inject
    private DataManager dataManager;

    @Override
    public BigDecimal getFxValue(String currency) {
        if (currency == null) {
            return BigDecimal.ZERO;
        }

        return dataManager.loadValue("select e.fxValue from gcsplatform_Fx e "
                + "where e.currency.currency = :currency "
                + "and :firstDayOfMonth <= e.billingDate", BigDecimal.class)
                .parameter("currency", currency)
                .parameter("firstDayOfMonth", getFirstDayOfMonth(), TemporalType.DATE)
                .optional()
                .orElse(BigDecimal.ZERO);
    }
}