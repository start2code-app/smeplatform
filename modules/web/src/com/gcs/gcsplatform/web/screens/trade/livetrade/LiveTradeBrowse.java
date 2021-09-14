package com.gcs.gcsplatform.web.screens.trade.livetrade;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.events.LiveTradeUpdatedEvent;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

@UiController("gcsplatform_LiveTrade.browse")
@UiDescriptor("live-trade-browse.xml")
public class LiveTradeBrowse extends TradeBrowse<LiveTrade> {

    @EventListener
    protected void liveTradeUpdatedEventListener(LiveTradeUpdatedEvent event) {
        tradesDl.load();
    }
}