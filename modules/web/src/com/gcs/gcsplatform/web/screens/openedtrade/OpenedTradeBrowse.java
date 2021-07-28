package com.gcs.gcsplatform.web.screens.openedtrade;

import javax.inject.Inject;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
@LookupComponent("openedTradesTable")
@LoadDataBeforeShow
public class OpenedTradeBrowse extends StandardLookup<OpenedTrade> {

    @Inject
    private CollectionLoader<OpenedTrade> openedTradesDl;

    @Install(to = "openedTradesTable.edit", subject = "afterCloseHandler")
    private void openedTradesTableEditAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
            openedTradesDl.load();
        }
    }
}