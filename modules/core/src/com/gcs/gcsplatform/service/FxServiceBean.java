package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;

@Service(FxService.NAME)
public class FxServiceBean implements FxService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public BigDecimal getFxValue(String currency, Date fxDate) {
        if (currency == null) {
            return null;
        }
        return dataManager.loadValue("select e.fxValue from gcsplatform_Fx e "
                + "where e.currency.currency = :currency "
                + "and :fxDate = e.billingDate", BigDecimal.class)
                .parameter("currency", currency)
                .parameter("fxDate", getFirstDayOfMonth(fxDate), TemporalType.DATE)
                .optional()
                .orElse(null);
    }
}