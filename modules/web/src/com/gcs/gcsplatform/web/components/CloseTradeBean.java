package com.gcs.gcsplatform.web.components;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
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
     * @param trade        Original trade
     * @param maturityDate Maturity date that sets to newly created ClosedTrade instance
     */
    public void close(Trade trade, Date maturityDate) {
        CommitContext commitContext = new CommitContext();
        createClosedTrade(trade, maturityDate, commitContext);
        commitContext.addInstanceToRemove(trade);
        dataManager.commit(commitContext);
    }

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then generates new contract number for
     * original trade.
     * <p>
     * Calculates PNL for newly created ClosedTrade/ClosedLiveTrade.
     *
     * @param trade        Original trade
     * @param maturityDate Maturity date that sets to newly created ClosedTrade instance. Also sets to Value date of
     *                     original trade
     */
    public void closeReopen(Trade trade, Date maturityDate) {
        CommitContext commitContext = new CommitContext();
        createClosedTrade(trade, maturityDate, commitContext);
        if (trade instanceof LiveTrade) {
            trade.setSubs(true);
            trade.setOrigtraderef(trade.getTraderef());
        }
        trade.setValueDate(maturityDate);
        trade.setTraderef(String.format(tradeConfig.getRefGenerationFormat(), getNextTradeRef()));
        commitContext.addInstanceToCommit(trade);
        dataManager.commit(commitContext);
    }

    private void createClosedTrade(Trade trade, Date maturityDate, CommitContext commitContext) {
        Trade closedTrade;
        if (trade instanceof LiveTrade) {
            closedTrade = dataManager.create(ClosedLiveTrade.class);
        } else {
            closedTrade = dataManager.create(ClosedTrade.class);
        }
        metadataTools.copy(trade, closedTrade);
        closedTrade.setMaturityDate(maturityDate);
        pnlCalculationBean.updatePnl(closedTrade);
        commitContext.addInstanceToCommit(closedTrade);
    }

    private long getNextTradeRef() {
        return uniqueNumbersService.getNextNumber(TRADE_REF_SEQUENCE);
    }
}
