package com.gcs.gcsplatform.web.screens.trade.tradecontainer;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.entity.trade.TradeContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

@UiDescriptor("trade-container-browse.xml")
@LookupComponent("tradeContainersTable")
@LoadDataBeforeShow
public abstract class TradeContainerBrowse<T extends TradeContainer> extends StandardLookup<T> {

    @Inject
    protected CollectionLoader<T> tradeContainersDl;

    /**
     * Updates list of trades on each close because the edit screen might be closed with discard in case of trade close.
     */
    @Install(to = "tradeContainersTable.edit", subject = "afterCloseHandler")
    protected void tradeContainersTableEditAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        tradeContainersDl.load();
    }

    @Install(to = "tradeContainersTable", subject = "styleProvider")
    protected String tradeContainersTableStyleProvider(T entity, String property) {
        Trade trade = entity.getTrade();
        if (property == null && (StringUtils.isEmpty(trade.getBuybroker()) || StringUtils.isEmpty(
                trade.getSellbroker()))) {
            return "v-table-row highlight-row";
        }
        return null;
    }
}