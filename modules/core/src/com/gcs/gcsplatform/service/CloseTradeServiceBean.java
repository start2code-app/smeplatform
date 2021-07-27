package com.gcs.gcsplatform.service;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.MetadataTools;
import org.springframework.stereotype.Service;

@Service(CloseTradeService.NAME)
public class CloseTradeServiceBean implements CloseTradeService {

    @Inject
    private DataManager dataManager;
    @Inject
    private MetadataTools metadataTools;

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
        // todo: originalTrade.setTraderef(); generate new contract number
        CommitContext commitContext = new CommitContext();
        commitContext.addInstanceToCommit(tradeContainer); // todo: do we need to remove and then create new OpenedTrade ?
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
}