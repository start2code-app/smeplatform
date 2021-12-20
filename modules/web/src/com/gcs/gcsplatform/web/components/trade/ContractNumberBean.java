package com.gcs.gcsplatform.web.components.trade;

import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import org.springframework.stereotype.Component;

@Component(ContractNumberBean.NAME)
public class ContractNumberBean {

    public static final String NAME = "gcsplatform_ContractNumberBean";

    private static final String TRADE_REF_SEQUENCE = "tradeRefSequence";

    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Inject
    private TradeConfig tradeConfig;

    public String getNextTradeRef() {
        long nextRefNumber = uniqueNumbersService.getNextNumber(TRADE_REF_SEQUENCE);
        return String.format(tradeConfig.getRefGenerationFormat(), nextRefNumber);
    }
}