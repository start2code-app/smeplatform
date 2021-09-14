package com.gcs.gcsplatform.web.components;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.TradeConfig;
import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.events.TradeClosedEvent;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.model.DataContext;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@Component(CloseTradeBean.NAME)
public class CloseTradeBean {

    public static final String NAME = "gcsplatform_CloseTradeBean";

    private static final String TRADE_REF_SEQUENCE = "tradeRefSequence";

    @Inject
    private MetadataTools metadataTools;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private Events events;
    @Inject
    private TradeConfig tradeConfig;
    @Inject
    private PnlCalculationBean pnlCalculationBean;

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then removes original trade.
     *
     * @param trade        Original trade
     * @param maturityDate Maturity date that sets to newly created ClosedTrade instance
     * @param dataContext  Screen data context
     */
    public void close(Trade trade, Date maturityDate, DataContext dataContext) {
        createClosedTrade(trade, maturityDate, dataContext);
        addPostCommitListener(dataContext);
        dataContext.remove(trade);
        dataContext.commit();
    }

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then generates new contract number for
     * original trade.
     *
     * @param trade        Original trade
     * @param maturityDate Maturity date that sets to newly created ClosedTrade instance. Also sets to Value date of
     *                     original trade
     * @param dataContext  Screen data context
     */
    public void closeReopen(Trade trade, Date maturityDate, DataContext dataContext) {
        createClosedTrade(trade, maturityDate, dataContext);
        if (trade instanceof LiveTrade) {
            trade.setSubs(true);
            trade.setOrigtraderef(trade.getTraderef());
        }
        trade.setValueDate(maturityDate);
        trade.setTradeDate(getCurrentDate());
        trade.setTraderef(getNextTradeRef());
        addPostCommitListener(dataContext);
        dataContext.commit();
    }

    private void createClosedTrade(Trade trade, Date maturityDate, DataContext dataContext) {
        Trade closedTrade;
        if (trade instanceof LiveTrade) {
            closedTrade = dataContext.create(ClosedLiveTrade.class);
        } else {
            closedTrade = dataContext.create(ClosedTrade.class);
        }
        metadataTools.copy(trade, closedTrade);
        closedTrade.setMaturityDate(maturityDate);
        closedTrade.setInvoiceDate(getCurrentDate());
        pnlCalculationBean.updatePnl(closedTrade);
    }

    private void addPostCommitListener(DataContext dataContext) {
        dataContext.addPostCommitListener(postCommitEvent -> {
            events.publish(new TradeClosedEvent(this));
        });
    }

    private String getNextTradeRef() {
        long nextRefNumber = uniqueNumbersService.getNextNumber(TRADE_REF_SEQUENCE);
        return String.format(tradeConfig.getRefGenerationFormat(), nextRefNumber);
    }
}
