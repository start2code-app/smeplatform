package com.gcs.gcsplatform.web.components.trade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.pnl.PnlCalculationBean;
import com.gcs.gcsplatform.web.events.TradeChangedEvent;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.model.DataContext;
import org.springframework.stereotype.Component;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@Component(CloseTradeBean.NAME)
public class CloseTradeBean {

    public static final String NAME = "gcsplatform_CloseTradeBean";

    @Inject
    private MetadataTools metadataTools;
    @Inject
    private Events events;
    @Inject
    private PnlCalculationBean pnlCalculationBean;
    @Inject
    private ContractNumberBean contractNumberBean;

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade.
     *
     * @param trade        Original trade
     * @param dataContext  Screen data context
     */
    public void newclose(Trade trade, DataContext dataContext) {
        Trade closedTrade;
        Trade liveTrade;
        closedTrade = dataContext.create(ClosedTrade.class);
        metadataTools.copy(trade, closedTrade);

        closedTrade.setTradeDate(new Date());
        closedTrade.setInvoiceDate(new Date());
        closedTrade.setValueDate(trade.getMaturityDate());
        closedTrade.setMaturityDate(null);
        closedTrade.setTraderef(contractNumberBean.getNextTradeRef());
        liveTrade = dataContext.create(LiveTrade.class);
        metadataTools.copy(closedTrade, liveTrade);
        pnlCalculationBean.updatePnl(closedTrade);
        addPostCommitListener(dataContext);
        dataContext.commit();
    }
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
        trade.setTraderef(contractNumberBean.getNextTradeRef());
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
            events.publish(new TradeChangedEvent(this));
        });
    }
}
