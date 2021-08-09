package com.gcs.gcsplatform.web.components;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.MetadataTools;
import org.springframework.stereotype.Component;

@Component(CloseTradeBean.NAME)
public class CloseTradeBean {

    public static final String NAME = "gcsplatform_CloseTradeBean";

    private static final String TRADE_REF_SEQUENCE = "tradeRefSequence";

    @Inject
    private DataManager dataManager;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private TradeConfig tradeConfig;
    @Inject
    private PnlCalculationBean pnlCalculationBean;

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then removes original trade.
     *
     * @param tradeContainer - Original trade
     * @param maturityDate   - Maturity date that sets to newly created ClosedTrade instance
     */
    public void close(TradeContainer tradeContainer, Date maturityDate) {
        CommitContext commitContext = new CommitContext();
        createClosedTrade(tradeContainer, maturityDate, commitContext);
        commitContext.addInstanceToRemove(tradeContainer);
        dataManager.commit(commitContext);
    }

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then generates new contract number for
     * original trade.
     *
     * Calculates PNL for newly created ClosedTrade/ClosedLiveTrade.
     *
     * @param tradeContainer - Original trade
     * @param maturityDate   - Maturity date that sets to newly created ClosedTrade instance. Also sets to Value date of
     *                       original trade
     */
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
        pnlCalculationBean.updatePnl(tradeCopy);
        closedTrade.setTrade(tradeCopy);
        commitContext.addInstanceToCommit(closedTrade);
    }

    private long getNextTradeRef() {
        return uniqueNumbersService.getNextNumber(TRADE_REF_SEQUENCE);
    }
}
