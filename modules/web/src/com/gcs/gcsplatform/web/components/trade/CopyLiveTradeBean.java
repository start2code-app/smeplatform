package com.gcs.gcsplatform.web.components.trade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.service.trade.TradeService;
import com.gcs.gcsplatform.util.DateUtils;
import com.gcs.gcsplatform.web.events.TradeChangedEvent;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.model.DataContext;
import org.springframework.stereotype.Component;

@Component(CopyLiveTradeBean.NAME)
public class CopyLiveTradeBean {

    public static final String NAME = "gcsplatform_CopyLiveTradeBean";

    @Inject
    private TradeService tradeService;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private Metadata metadata;
    @Inject
    private Events events;

    /**
     * Updates corresponding live trade with all of the changes of closed trade.
     *
     * @param closedTrade Closed trade
     * @param dataContext Screen data context
     */
    public void update(ClosedTrade closedTrade, DataContext dataContext) {
        if (!DateUtils.isDateInCurrentMonth(closedTrade.getInvoiceDate())) {
            return;
        }
        LiveTrade liveTrade = tradeService.findTrade(LiveTrade.class, closedTrade.getTraderef(),
                ViewBuilder.of(LiveTrade.class)
                        .addView(View.LOCAL)
                        .build());
        if (liveTrade != null) {
            metadataTools.copy(closedTrade, liveTrade);
            dataContext.setModified(dataContext.merge(liveTrade), true);
            dataContext.addPostCommitListener(postCommitEvent -> {
                events.publish(new TradeChangedEvent(this));
            });
        }
    }

    /**
     * Creates a live trade copy of a closed trade.
     *
     * @param closedTrade Closed trade
     * @param dataContext Screen data context
     */
    public void create(ClosedTrade closedTrade, DataContext dataContext) {
        LiveTrade liveTrade = metadata.create(LiveTrade.class);
        metadataTools.copy(closedTrade, liveTrade);
        dataContext.setModified(dataContext.merge(liveTrade), true);
        dataContext.addPostCommitListener(postCommitEvent -> {
            events.publish(new TradeChangedEvent(this));
        });
    }
}