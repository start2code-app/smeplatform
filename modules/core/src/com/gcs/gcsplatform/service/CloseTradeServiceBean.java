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
        CommitContext commitContext = new CommitContext();
        createClosedTrade(tradeContainer, maturityDate, commitContext);
        commitContext.addInstanceToRemove(tradeContainer);
        dataManager.commit(commitContext);
    }

    @Override
    public void closeReopen(TradeContainer tradeContainer, Date maturityDate) {
        CommitContext commitContext = new CommitContext();
        createClosedTrade(tradeContainer, maturityDate, commitContext);
        Trade originalTrade = tradeContainer.getTrade();
        originalTrade.setValueDate(maturityDate);
        originalTrade.setTraderef(String.format(tradeConfig.getRefGenerationFormat(), getNextTradeRef()));
        commitContext.addInstanceToCommit(tradeContainer);
        dataManager.commit(commitContext);
    }

    private void createClosedTrade(TradeContainer tradeContainer, Date maturityDate, CommitContext commitContext) {
        Class<? extends TradeContainer> closedTradeClass;
        if (tradeContainer instanceof LiveTrade) {
            closedTradeClass = ClosedLiveTrade.class;
        } else {
            closedTradeClass = ClosedTrade.class;
        }
        TradeContainer closedTrade = dataManager.create(closedTradeClass);
        Trade tradeCopy = metadataTools.copy(tradeContainer.getTrade());
        tradeCopy.setMaturityDate(maturityDate);
        closedTrade.setTrade(tradeCopy);
        commitContext.addInstanceToCommit(closedTrade);
    }

    private long getNextTradeRef() {
        return uniqueNumbers.getNextNumber(TRADE_REF_SEQUENCE);
    }
}