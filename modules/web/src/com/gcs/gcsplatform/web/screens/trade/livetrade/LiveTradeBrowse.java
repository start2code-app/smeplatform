package com.gcs.gcsplatform.web.screens.trade.livetrade;

import java.util.Set;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.events.LiveTradeUpdatedEvent;
import com.gcs.gcsplatform.web.screens.clpboard.SimpleCopyScreen;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

@UiController("gcsplatform_LiveTrade.browse")
@UiDescriptor("live-trade-browse.xml")
public class LiveTradeBrowse extends TradeBrowse<LiveTrade> {

    @Inject
    private GroupTable<Trade> tradesTable;
    @Inject
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        tradesTable.setMultiSelect(true);
        
    }

    @Subscribe("cpySimplrBtn")
    public void onCpySimplrBtnClick(Button.ClickEvent event) {

        Set<Trade> selected = tradesTable.getSelected();
        if (selected.isEmpty()) {

        notifications.create(Notifications.NotificationType.WARNING)
                .withCaption("Please make a selection(s)"
                )
                .show();
        return;
        }


        SimpleCopyScreen scs = screenBuilders.screen(this).withScreenClass(SimpleCopyScreen.class).withOpenMode(OpenMode.DIALOG).build();
        scs.setSetlected(selected);
        scs.show();

    }




    @EventListener
    protected void liveTradeUpdatedEventListener(LiveTradeUpdatedEvent event) {
        tradesDl.load();
    }
}