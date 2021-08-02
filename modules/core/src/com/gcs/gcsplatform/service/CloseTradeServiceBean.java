package com.gcs.gcsplatform.service;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.MetadataTools;
import org.springframework.stereotype.Service;

@Service(CloseTradeService.NAME)
public class CloseTradeServiceBean implements CloseTradeService {

    private static final String TRADE_REF_SEQUENCE = "tradeRefSequence";

    @Inject
    private DataManager dataManager;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private UniqueNumbersAPI uniqueNumbers;
    @Inject
    private TradeConfig tradeConfig;

    @Override
    public <T extends TradeContainer> void close(T tradeContainer, Date maturityDate) {
        ClosedTrade closedTrade = createClosedTrade(tradeContainer.getTrade(), maturityDate);
        CommitContext commitContext = new CommitContext();
        commitContext.addInstanceToRemove(tradeContainer);
        commitContext.addInstanceToCommit(closedTrade);
        dataManager.commit(commitContext);
    }

    @Override
    public <T extends TradeContainer> void closeReopen(T tradeContainer, Date maturityDate) {
        Trade originalTrade = tradeContainer.getTrade();
        ClosedTrade closedTrade = createClosedTrade(originalTrade, maturityDate);
        originalTrade.setValueDate(maturityDate);
        originalTrade.setTraderef(String.format(tradeConfig.getRefGenerationFormat(), getNextTradeRef()));
        CommitContext commitContext = new CommitContext();
        commitContext.addInstanceToCommit(tradeContainer);
        commitContext.addInstanceToCommit(closedTrade);
        dataManager.commit(commitContext);
    }

    private ClosedTrade createClosedTrade(Trade trade, Date maturityDate) {
        ClosedTrade closedTrade = dataManager.create(ClosedTrade.class);
        Trade tradeCopy = metadataTools.copy(trade);
        tradeCopy.setMaturityDate(maturityDate);
        closedTrade.setTrade(tradeCopy);
        return closedTrade;
    }

    private long getNextTradeRef() {
        return uniqueNumbers.getNextNumber(TRADE_REF_SEQUENCE);
    }
}