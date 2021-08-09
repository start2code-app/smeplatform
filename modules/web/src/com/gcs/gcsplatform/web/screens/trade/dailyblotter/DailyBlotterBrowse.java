package com.gcs.gcsplatform.web.screens.trade.dailyblotter;

import java.util.Collection;
import java.util.Date;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.screens.trade.tradecontainer.TradeContainerBrowse;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;

@UiController("gcsplatform_DailyBlotter.browse")
@UiDescriptor("daily-blotter-browse.xml")
public class DailyBlotterBrowse extends TradeContainerBrowse<ClosedTrade> {

    @Override
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Date today = new Date();
        Date tomorrow = DateUtils.addDays(today, 1);
        Collection<Trade> trades = tradeService.getEnrichedTradesForPnlChart(getTradeClass(), today, tomorrow);
        pnlChartBean.showPnlChartScreen(trades, this);
    }

    @Override
    public Class<ClosedTrade> getTradeClass() {
        return ClosedTrade.class;
    }
}