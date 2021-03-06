package com.gcs.gcsplatform.web.screens.trade.livetrade;

import java.util.Set;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.screens.clpboard.SimpleCopyScreen;
import com.gcs.gcsplatform.web.screens.trade.TradeBrowse;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.browse")
@UiDescriptor("live-trade-browse.xml")
public class LiveTradeBrowse extends TradeBrowse<LiveTrade> {

    @Inject
    protected GroupTable<Trade> tradesTable;

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
}