package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import org.apache.commons.lang3.StringUtils;

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

    @Install(to = "openedTradesTable", subject = "styleProvider")
    private String openedTradesTableStyleProvider(OpenedTrade entity, String property) {
        Trade trade = entity.getTrade();
        if (property == null && (StringUtils.isEmpty(trade.getBuybroker()) || StringUtils.isEmpty(
                trade.getSellbroker()))) {
            return "highlight-row";
        }
        return null;
    }
}