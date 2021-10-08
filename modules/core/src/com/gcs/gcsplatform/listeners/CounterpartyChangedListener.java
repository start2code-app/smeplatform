package com.gcs.gcsplatform.listeners;

import java.util.Date;
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

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;
import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

/**
 * Backports counterparty changes to related trades of current month and invokes PNL recalculation.
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
        Date currentDate = getCurrentDate();
        closedTradePnlUpdateService.updatePnl(counterparty, getFirstDayOfMonth(currentDate), getLastDayOfMonth(currentDate));
    }
}