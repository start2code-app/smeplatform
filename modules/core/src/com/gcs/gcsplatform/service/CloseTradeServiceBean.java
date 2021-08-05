package com.gcs.gcsplatform.service;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
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
    public void close(TradeContainer tradeContainer, Date maturityDate) {
        ClosedTrade closedTrade = createClosedTrade(tradeContainer, maturityDate);
        CommitContext commitContext = new CommitContext();
        commitContext.addInstanceToRemove(tradeContainer);
        commitContext.addInstanceToCommit(closedTrade);
        dataManager.commit(commitContext);
    }

    @Override
    public void closeReopen(TradeContainer tradeContainer, Date maturityDate) {
        ClosedTrade closedTrade = createClosedTrade(tradeContainer, maturityDate);
        Trade originalTrade = tradeContainer.getTrade();
        originalTrade.setValueDate(maturityDate);
        originalTrade.setTraderef(String.format(tradeConfig.getRefGenerationFormat(), getNextTradeRef()));
        CommitContext commitContext = new CommitContext();
        commitContext.addInstanceToCommit(tradeContainer);
        commitContext.addInstanceToCommit(closedTrade);
        dataManager.commit(commitContext);
    }

    private ClosedTrade createClosedTrade(TradeContainer tradeContainer, Date maturityDate) {
        Trade trade = tradeContainer.getTrade();
        if (tradeContainer instanceof LiveTrade) {
            return createClosedTrade(trade, maturityDate, ClosedLiveTrade.class);
        } else {
            return createClosedTrade(trade, maturityDate, ClosedTrade.class);
        }
    }

    private <T extends ClosedTrade> T createClosedTrade(Trade trade, Date maturityDate, Class<T> closedTradeClass) {
        T closedTrade = dataManager.create(closedTradeClass);
        Trade tradeCopy = metadataTools.copy(trade);
        tradeCopy.setMaturityDate(maturityDate);
        closedTrade.setTrade(tradeCopy);
        return closedTrade;
    }

    private long getNextTradeRef() {
        return uniqueNumbers.getNextNumber(TRADE_REF_SEQUENCE);
    }
}