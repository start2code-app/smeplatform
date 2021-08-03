package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

@UiController("gcsplatform_OpenedTrade.browse")
@UiDescriptor("opened-trade-browse.xml")
@LookupComponent("openedTradesTable")
@LoadDataBeforeShow
public class OpenedTradeBrowse extends StandardLookup<OpenedTrade> {

    @Inject
    protected CollectionLoader<OpenedTrade> openedTradesDl;

    /**
     * Updates list of trades on each close because the edit screen might be closed with discard in case of trade close.
     */
    @Install(to = "openedTradesTable.edit", subject = "afterCloseHandler")
    protected void openedTradesTableEditAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        openedTradesDl.load();
    }

    @Install(to = "openedTradesTable", subject = "styleProvider")
    protected String openedTradesTableStyleProvider(OpenedTrade entity, String property) {
        Trade trade = entity.getTrade();
        if (property == null && (StringUtils.isEmpty(trade.getBuybroker()) || StringUtils.isEmpty(
                trade.getSellbroker()))) {
            return "v-table-row highlight-row";
        }
        return null;
    }
}