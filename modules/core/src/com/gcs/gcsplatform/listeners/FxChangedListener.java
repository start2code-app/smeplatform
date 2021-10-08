package com.gcs.gcsplatform.listeners;

import java.util.Date;
import java.util.UUID;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Fx;
import com.gcs.gcsplatform.service.pnl.ClosedTradePnlUpdateService;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

/**
 * Invokes PNL recalculation on closed trades after updating FX.
 */
@Component("gcsplatform_FxChangedListener")
public class FxChangedListener {

    @Inject
    private DataManager dataManager;
    @Inject
    private ClosedTradePnlUpdateService closedTradePnlUpdateService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(EntityChangedEvent<Fx, UUID> event) {
        Fx fx = dataManager.load(Fx.class)
                .id(event.getEntityId().getValue())
                .view(ViewBuilder.of(Fx.class)
                        .add("currency", View.MINIMAL)
                        .addView(View.LOCAL)
                        .build())
                .one();
        Date billingDate = fx.getBillingDate();
        String currency = fx.getCurrency().getCurrency();
        closedTradePnlUpdateService.updatePnl(currency, getFirstDayOfMonth(billingDate), getLastDayOfMonth(billingDate));
    }
}