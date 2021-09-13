package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import java.util.Collection;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
public class OpenedTradeBrowse extends TradeBrowse<OpenedTrade> {

    @Override
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Collection<OpenedTrade> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass(),
                ViewBuilder.of(getTradeClass())
                        .addView(View.LOCAL)
                        .build());
        pnlCalculationBean.recalculatePnl(trades);
        showPnlChartScreen(trades);
    }

    @Override
    public Class<OpenedTrade> getTradeClass() {
        return OpenedTrade.class;
    }
}