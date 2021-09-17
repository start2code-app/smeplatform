package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.CurrencyConfig;
import com.haulmont.cuba.core.global.DataManager;
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
    public BigDecimal findFxValue(String currency, Date billingDate) {
        if (currency == null || billingDate == null) {
            return null;
        }
        return dataManager.loadValue("select e.fxValue from gcsplatform_Fx e "
                + "where e.currency.currency = :currency "
                + "and e.billingDate = :billingDate", BigDecimal.class)
                .parameter("currency", currency)
                .parameter("billingDate", getFirstDayOfMonth(billingDate))
                .optional()
                .orElse(null);
    }

    @Nullable
    @Override
    public BigDecimal findUsdFxValue(Date fxDate) {
        return findFxValue(currencyConfig.getUsdCurrency().getCurrency(), fxDate);
    }
}