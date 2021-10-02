package com.gcs.gcsplatform.service.pnl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.gcs.gcsplatform.service.BrokerageService;
import com.gcs.gcsplatform.service.trade.TradeService;
import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

@Service(ClosedTradePnlUpdateService.NAME)
public class ClosedTradePnlUpdateServiceBean implements ClosedTradePnlUpdateService {

    @Inject
    private PnlBulkCalculationService pnlBulkCalculationService;
    @Inject
    private TradeService tradeService;
    @Inject
    private BrokerageService brokerageService;
    @Inject
    private DataManager dataManager;

    @Override
    public void updatePnl() {
        Collection<ClosedTrade> trades = tradeService.getTrades(ClosedTrade.class,
                ViewBuilder.of(ClosedTrade.class)
                        .addView(View.LOCAL)
                        .build());
        calculatePnlAndSave(trades);
    }

    @Override
    public void updatePnl(String currency, Date billingDate) {
        Collection<ClosedTrade> trades = tradeService.getTradesByCurrency(ClosedTrade.class, currency,
                getFirstDayOfMonth(billingDate), getLastDayOfMonth(billingDate), ViewBuilder.of(ClosedTrade.class)
                        .addView(View.LOCAL)
                        .build());
        calculatePnlAndSave(trades);
    }

    @Override
    public void updatePnl(Counterparty counterparty) {
        Preconditions.checkNotNullArgument(counterparty);
        Date currentDate = getCurrentDate();
        String counterpartyName = counterparty.getCounterparty();
        Collection<ClosedTrade> trades = tradeService.getTradesByCounterparty(ClosedTrade.class, counterpartyName,
                getFirstDayOfMonth(currentDate), getLastDayOfMonth(currentDate), ViewBuilder.of(ClosedTrade.class)
                        .addView(View.LOCAL)
                        .build());
        for (ClosedTrade trade : trades) {
            TradeSide side;
            if (counterpartyName.equals(trade.getBuyer())) {
                side = TradeSide.BUY;
            } else {
                side = TradeSide.SELL;
            }
            if (!(Boolean.TRUE.equals(trade.getBrooveride()) || Boolean.TRUE.equals(trade.getSubs()))) {
                BigDecimal brokerage = brokerageService.findBrokerageValue(counterpartyName, trade.getCategory(),
                        trade.getBrokerageType());
                trade.setBrokerage(brokerage, side);
            }
            trade.setCounterparty(counterpartyName, side);
            trade.setCash(counterparty.getCash(), side);
            trade.setCounterpartyCode(counterparty.getCode(), side);
            trade.setLocation(counterparty.getLocation().getName(), side);
            trade.setCommissionOverride(counterparty.getCommissionOverride(), side);
        }
        calculatePnlAndSave(trades);
    }

    private void calculatePnlAndSave(Collection<ClosedTrade> trades) {
        pnlBulkCalculationService.bulkCalculatePnl(trades);
        CommitContext commitContext = new CommitContext();
        for (ClosedTrade trade : trades) {
            commitContext.addInstanceToCommit(trade);
        }
        dataManager.commit(commitContext);
    }
}