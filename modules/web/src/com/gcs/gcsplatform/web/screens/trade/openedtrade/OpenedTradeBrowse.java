package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
public class OpenedTradeBrowse extends TradeBrowse<OpenedTrade> {

    @Inject
    protected PnlCalculationBean pnlCalculationBean;

    @Override
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Collection<OpenedTrade> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass(),
                ViewBuilder.of(getTradeClass())
                        .addView(View.LOCAL)
                        .build());
        recalculatePnl(trades);
        showPnlChartScreen(trades);
    }

    /**
     * Recalculates PNL assuming maturity date is today.
     * <p>
     * Note: recalculated PNL value is not being persisted, it is only used in chart.
     *
     * @param trades Trades
     */
    @Override
    protected void recalculatePnl(Collection<OpenedTrade> trades) {
        for (OpenedTrade openedTrade : trades) {
            openedTrade.setMaturityDate(new Date());
            pnlCalculationBean.updatePnl(openedTrade);
        }
    }

    @Override
    public Class<OpenedTrade> getTradeClass() {
        return OpenedTrade.class;
    }
}