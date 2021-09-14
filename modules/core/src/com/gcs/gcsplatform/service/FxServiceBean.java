package com.gcs.gcsplatform.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.gcs.gcsplatform.config.CurrencyConfig;
import com.haulmont.cuba.core.global.DataManager;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;

@Service(FxService.NAME)
public class FxServiceBean implements FxService {

    @Inject
    private DataManager dataManager;
    @Inject
    private CurrencyConfig currencyConfig;

    @Nullable
    @Override
    public BigDecimal getFxValue(String currency, Date fxDate) {
        if (currency == null || fxDate == null) {
            return null;
        }
        Date fxDateStart = DateUtils.truncate(fxDate, Calendar.MONTH);
        Date fxDateEnd = DateUtils.addDays(fxDateStart, 1);
        return dataManager.loadValue("select e.fxValue from gcsplatform_Fx e "
                + "where e.currency.currency = :currency "
                + "and e.billingDate >= :fxDateStart "
                + "and e.billingDate < :fxDateEnd", BigDecimal.class)
                .parameter("currency", currency)
                .parameter("fxDateStart", fxDateStart, TemporalType.DATE)
                .parameter("fxDateEnd", fxDateEnd, TemporalType.DATE)
                .optional()
                .orElse(null);
    }

    @Nullable
    @Override
    public BigDecimal getUsdFxValue(Date fxDate) {
        return getFxValue(currencyConfig.getUsdCurrency().getCurrency(), fxDate);
    }
}