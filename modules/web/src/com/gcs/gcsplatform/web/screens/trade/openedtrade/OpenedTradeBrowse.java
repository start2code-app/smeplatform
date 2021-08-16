package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
public class OpenedTradeBrowse extends TradeContainerBrowse<OpenedTrade> {

    @Inject
    protected PnlCalculationBean pnlCalculationBean;

    @Override
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Collection<OpenedTrade> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass());
        recalculatePnl(trades);
        showPnlChartScreen(trades);
    }

    /**
     * Recalculates PNL assuming maturity date is today.
     * <p>
     * Note: recalculated PNL value is not being persisted, it is only used in chart.
     */
    protected void recalculatePnl(Collection<OpenedTrade> openedTrades) {
        for (OpenedTrade openedTrade : openedTrades) {
            Trade trade = openedTrade.getTrade();
            trade.setMaturityDate(new Date());
            pnlCalculationBean.updatePnl(trade);
        }
    }

    @Override
    public Class<OpenedTrade> getTradeClass() {
        return OpenedTrade.class;
    }
}