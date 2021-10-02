package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.CurrencyConfig;
import com.gcs.gcsplatform.config.FxProviderConfig;
import com.gcs.gcsplatform.core.fxprovider.FxProviderAPI;
import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.masterdata.Fx;
import com.gcs.gcsplatform.service.CurrencyService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.security.app.Authentication;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;

@Service(FxScheduledUpdateService.NAME)
public class FxScheduledUpdateServiceBean implements FxScheduledUpdateService {

    @Inject
    private FxProviderConfig fxProviderConfig;
    @Inject
    private CurrencyConfig currencyConfig;
    @Inject
    private CurrencyService currencyService;
    @Inject
    private FxService fxService;
    @Inject
    private FxProviderAPI fxProviderAPI;
    @Inject
    private DataManager dataManager;
    @Inject
    private Logger log;
    @Inject
    private Authentication authentication;

    @Override
    public void updateFxForAllCurrencies() {
        Currency gbpCurrency = currencyConfig.getGbpCurrency();
        List<Currency> currencies = currencyService.getCurrencies(ViewBuilder.of(Currency.class)
                .addView(View.MINIMAL)
                .build())
                .stream()
                .filter(currency -> !gbpCurrency.equals(currency))
                .collect(Collectors.toList());
        updateFx(gbpCurrency, BigDecimal.ONE);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            private final Iterator<Currency> iterator = currencies.iterator();

            @Override
            public void run() {
                try {
                    authentication.begin();
                    if (iterator.hasNext()) {
                        Currency nextCurrency = iterator.next();
                        BigDecimal updatedFx = fxProviderAPI.getFx(gbpCurrency.getCurrency(), nextCurrency.getCurrency());
                        updateFx(nextCurrency, updatedFx);
                    } else {
                        scheduler.shutdown();
                    }
                } catch (Exception e) {
                    log.error("An error occurred while updating FX", e);
                    scheduler.shutdown();
                } finally {
                    authentication.end();
                }
            }
        }, 0, fxProviderConfig.getDelay(), TimeUnit.SECONDS);
    }

    private void updateFx(Currency currency, BigDecimal updatedFx) {
        Date currentDate = getCurrentDate();
        Fx fx = fxService.findFx(currency, currentDate);
        if (fx != null) {
            fx.setFxValue(updatedFx);
        } else {
            fx = dataManager.create(Fx.class);
            fx.setCurrency(currency);
            fx.setBillingDate(getFirstDayOfMonth(currentDate));
            fx.setFxValue(updatedFx);
        }
        dataManager.commit(fx);
    }
}