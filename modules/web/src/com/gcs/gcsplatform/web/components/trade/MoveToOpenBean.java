package com.gcs.gcsplatform.web.components.trade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.events.TradeChangedEvent;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.model.DataContext;
import org.springframework.stereotype.Component;


@Component(MoveToOpenBean.NAME)
public class MoveToOpenBean {

    public static final String NAME = "gcsplatform_MoveToOpenBean";

    @Inject
    private MetadataTools metadataTools;
    @Inject
    private Events events;

    /**
     * Creates ClosedTrade/ClosedLiveTrade instance based on provided trade and then removes original trade.
     *
     * @param trade       Original trade
     * @param dataContext Screen data context
     */
    public void moveToOpen(Trade trade, DataContext dataContext) {
        createOpenTrade(trade, dataContext);
        addPostCommitListener(dataContext);
        dataContext.remove(trade);
        dataContext.commit();
    }


    private void createOpenTrade(Trade trade, DataContext dataContext) {
        Trade openTrade = dataContext.create(OpenedTrade.class);
        metadataTools.copy(trade, openTrade);
    }

    private void addPostCommitListener(DataContext dataContext) {
        dataContext.addPostCommitListener(postCommitEvent -> {
            events.publish(new TradeChangedEvent(this));
        });
    }
}
