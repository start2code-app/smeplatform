package com.gcs.gcsplatform.web.screens.trade.dailyblotter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.trade.TradeService;
import com.gcs.gcsplatform.web.components.pnl.PnlChartBean;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.gcs.gcsplatform.web.screens.trade.closedtrade.ClosedTradeEdit;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@UiController("gcsplatform_DailyBlotter.browse")
@UiDescriptor("daily-blotter-browse.xml")
public class DailyBlotterBrowse extends TradeBrowse<ClosedTrade> {

    @Inject
    protected TradeService tradeService;
    @Inject
    protected PnlChartBean pnlChartBean;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Messages messages;

    @Subscribe("pnlChartBtn")
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Date today = getCurrentDate();
        Date tomorrow = DateUtils.addDays(today, 1);
        Collection<ClosedTrade> trades = tradeService.getEnrichedTradesForPnlChart(ClosedTrade.class,
                today, tomorrow, ViewBuilder.of(ClosedTrade.class)
                        .addView(View.LOCAL)
                        .build()
        );
        DateFormat dateFormat = new SimpleDateFormat(messages.getMainMessage("dateFormat"));
        pnlChartBean.showPnlChartScreen(this, trades, messageBundle.getMessage("dailyPnl.caption"),
                dateFormat.format(today));
    }

    @Install(to = "tradesTable.create", subject = "screenConfigurer")
    protected void tradesTableCreateScreenConfigurer(Screen screen) {
        ((ClosedTradeEdit) screen).setNewDailyBlotterTrade(true);
    }

    @Inject
    private Button refreshBtn;

    @Subscribe
    public void onInit(InitEvent event) {



    }
}