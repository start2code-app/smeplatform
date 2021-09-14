package com.gcs.gcsplatform.service.pnl;

import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.TradeService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Service;

@Service(ClosedTradePnlUpdateService.NAME)
public class ClosedTradePnlUpdateServiceBean implements ClosedTradePnlUpdateService {

    @Inject
    private PnlBulkCalculationService pnlBulkCalculationService;
    @Inject
    private TradeService tradeService;
    @Inject
    private DataManager dataManager;

    @Override
    public void updatePnl() {
        Collection<ClosedTrade> trades = tradeService.getClosedTradesWithoutPnl(ViewBuilder.of(ClosedTrade.class)
                .addView(View.LOCAL)
                .build());
        calculatePnlAndSave(trades);
    }

    @Override
    public void updatePnl(String currency, Date billingDate) {
        Collection<ClosedTrade> trades = tradeService.getClosedTradesToUpdateFx(currency, billingDate, ViewBuilder.of(
                ClosedTrade.class)
                .addView(View.LOCAL)
                .build());
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