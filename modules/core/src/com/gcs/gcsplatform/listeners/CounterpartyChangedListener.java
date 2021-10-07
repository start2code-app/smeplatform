package com.gcs.gcsplatform.listeners;

import java.util.UUID;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.service.pnl.ClosedTradePnlUpdateService;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Backports counterparty changes to related trades and invokes PNL recalculation.
 */
@Component("gcsplatform_CounterpartyChangedListener")
public class CounterpartyChangedListener {

    @Inject
    private DataManager dataManager;
    @Inject
    private ClosedTradePnlUpdateService closedTradePnlUpdateService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(EntityChangedEvent<Counterparty, UUID> event) {
        Counterparty counterparty = dataManager.load(Counterparty.class)
                .id(event.getEntityId().getValue())
                .view(ViewBuilder.of(Counterparty.class)
                        .add("location", View.MINIMAL)
                        .addView(View.LOCAL)
                        .build())
                .one();
        closedTradePnlUpdateService.updatePnl(counterparty);
    }
}