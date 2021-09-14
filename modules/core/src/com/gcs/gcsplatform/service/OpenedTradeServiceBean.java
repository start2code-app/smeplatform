package com.gcs.gcsplatform.service;

import java.util.Collection;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.service.pnl.PnlBulkCalculationService;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@Service(OpenedTradeService.NAME)
public class OpenedTradeServiceBean implements OpenedTradeService {

    @Inject
    private TradeService tradeService;
    @Inject
    private PnlBulkCalculationService pnlBulkCalculationService;

    @Override
    public Collection<OpenedTrade> getOpenedTradesForPnlChart() {
        Collection<OpenedTrade> trades = tradeService.getEnrichedTradesForPnlChart(OpenedTrade.class,
                ViewBuilder.of(OpenedTrade.class)
                        .addView(View.LOCAL)
                        .build());
        for (OpenedTrade trade : trades) {
            trade.setMaturityDate(getCurrentDate());
        }
        pnlBulkCalculationService.bulkCalculatePnl(trades);
        return trades;
    }
}