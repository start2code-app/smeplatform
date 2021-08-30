package com.gcs.gcsplatform.web.components;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.service.TradeService;
import com.gcs.gcsplatform.util.DateUtils;
import com.gcs.gcsplatform.web.events.LiveTradeUpdatedEvent;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.model.DataContext;
import org.springframework.stereotype.Component;

@Component(UpdateCorrespondingLiveTradeBean.NAME)
public class UpdateCorrespondingLiveTradeBean {

    public static final String NAME = "gcsplatform_UpdateCorrespondingLiveTradeBean";

    @Inject
    private TradeService tradeService;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private Events events;

    public void update(ClosedTrade closedTrade, DataContext dataContext) {
        if (!DateUtils.isDateInCurrentMonth(closedTrade.getInvoiceDate())) {
            return;
        }
        LiveTrade liveTrade = tradeService.getCorrespondingLiveTrade(closedTrade,
                ViewBuilder.of(LiveTrade.class)
                        .addView(View.LOCAL)
                        .build());
        if (liveTrade != null) {
            metadataTools.copy(closedTrade, liveTrade);
            dataContext.setModified(dataContext.merge(liveTrade), true);
            dataContext.addPostCommitListener(postCommitEvent -> {
                events.publish(new LiveTradeUpdatedEvent(this));
            });
        }
    }
}