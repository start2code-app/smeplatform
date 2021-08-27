package com.gcs.gcsplatform.web.screens.trade.dailyblotter;

import java.util.Collection;
import java.util.Date;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.web.screens.trade.closedtrade.ClosedTradeBrowse;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;

@UiController("gcsplatform_DailyBlotter.browse")
@UiDescriptor("daily-blotter-browse.xml")
public class DailyBlotterBrowse extends ClosedTradeBrowse {

    @Override
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Date today = new Date();
        Date tomorrow = DateUtils.addDays(today, 1);
        Collection<ClosedTrade> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass(),
                ViewBuilder.of(getTradeClass())
                        .addView(View.LOCAL)
                        .build(),
                today,
                tomorrow);
        recalculatePnl(trades);
        showPnlChartScreen(trades);
    }
}