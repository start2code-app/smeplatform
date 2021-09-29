package com.gcs.gcsplatform.service.fx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.CurrencyConfig;
import com.gcs.gcsplatform.entity.trade.Trade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.BigDecimalUtils.isAnyNullOrZero;

@Service(FxCalculationService.NAME)
public class FxCalculationServiceBean implements FxCalculationService {

    @Inject
    private FxService fxService;
    @Inject
    private CurrencyConfig currencyConfig;

    @Override
    public BigDecimal calculateGbpEquivalent(BigDecimal amount, BigDecimal fx) {
        if (isAnyNullOrZero(amount, fx)) {
            return BigDecimal.ZERO;
        }
        return amount.divide(fx, 2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateEquivalent(BigDecimal amount, BigDecimal baseFx, BigDecimal targetFx) {
        if (isAnyNullOrZero(amount, baseFx, targetFx)) {
            return BigDecimal.ZERO;
        }
        BigDecimal crossRate = calculateCrossRate(baseFx, targetFx);
        return amount.multiply(crossRate).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateCrossRate(Trade trade) {
        String bondCurrency = trade.getBondCurrency();
        String repoCurrency = trade.getRepoCurrency();
        if (StringUtils.isAnyBlank(bondCurrency, repoCurrency)) {
            return BigDecimal.ZERO;
        }
        if (bondCurrency.equals(repoCurrency) || currencyConfig.getDefaultCurrencies().contains(bondCurrency)) {
            return BigDecimal.ONE;
        }
        BigDecimal repoFx = trade.getFx();
        BigDecimal bondFx = fxService.findFxValue(bondCurrency, trade.getInvoiceDate());
        return calculateCrossRate(bondFx, repoFx);
    }

    @Override
    public BigDecimal calculateCrossRate(BigDecimal baseFx, BigDecimal targetFx) {
        if (isAnyNullOrZero(baseFx, targetFx)) {
            return BigDecimal.ZERO;
        }
        return targetFx.divide(baseFx, 4, RoundingMode.HALF_UP);
    }
}