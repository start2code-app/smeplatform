package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import java.util.Collection;
import java.util.Set;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.trade.OpenedTradeService;
import com.gcs.gcsplatform.web.components.pnl.PnlChartBean;
import com.gcs.gcsplatform.web.screens.clpboard.SimpleCopyScreen;
import com.gcs.gcsplatform.web.screens.rerate.RerateScreen;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
public class OpenedTradeBrowse extends TradeBrowse<OpenedTrade> {


    @Inject
    protected PnlChartBean pnlChartBean;
    @Inject
    protected OpenedTradeService openedTradeService;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected GroupTable<Trade> tradesTable;
    @Inject
    private Notifications notifications;

    @Subscribe("pnlChartBtn")
    protected void onPnlChartBtnClick(Button.ClickEvent event) {
        Collection<OpenedTrade> trades = openedTradeService.getOpenedTradesForPnlChart();
        pnlChartBean.showPnlChartScreen(this, trades, messageBundle.getMessage("openedTradesPnl.caption"));
    }

    @Subscribe("tradesTable.simpleCopy")
    protected void onTradesTableSimpleCopy(Action.ActionPerformedEvent event) {
        Set<Trade> selected = tradesTable.getSelected();
        SimpleCopyScreen scs = screenBuilders.screen(this)
                .withScreenClass(SimpleCopyScreen.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();
        scs.setSelected(selected);
        scs.show();
    }

    @Subscribe("reratetBtn")
    public void onReratetBtnClick(Button.ClickEvent event) {
        Set<Trade> selected = tradesTable.getSelected();
        if (selected.isEmpty()) {

            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Please make a selection(s)"
                    )
                    .show();
        }

        else {
            RerateScreen rrs = screenBuilders.screen(this)
                    .withScreenClass(RerateScreen.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .build();
            rrs.setSelected(selected);
            rrs.show();
        }
    }

}