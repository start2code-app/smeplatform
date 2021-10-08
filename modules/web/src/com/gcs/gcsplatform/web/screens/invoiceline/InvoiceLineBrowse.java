package com.gcs.gcsplatform.web.screens.invoiceline;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.SnapshotConfig;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.invoice.InvoiceSnapshotService;
import com.gcs.gcsplatform.service.trade.TradeService;
import com.gcs.gcsplatform.web.components.invoice.InvoiceBackportBean;
import com.gcs.gcsplatform.web.components.invoice.InvoiceCalculationBean;
import com.gcs.gcsplatform.web.events.InvoiceUpdatedEvent;
import com.gcs.gcsplatform.web.util.ScreenUtil;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.RemoveOperation;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.backgroundwork.BackgroundWorkWindow;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfPreviousMonth;
import static com.gcs.gcsplatform.util.DateUtils.getPreviousMonth;

@UiController("gcsplatform_InvoiceLine.browse")
@UiDescriptor("invoice-line-browse.xml")
@LookupComponent("invoiceLinesTable")
@LoadDataBeforeShow
public class InvoiceLineBrowse extends StandardLookup<InvoiceLine> {

    @Inject
    protected GroupTable<InvoiceLine> invoiceLinesTable;

    @Inject
    protected TradeService tradeService;
    @Inject
    protected InvoiceCalculationBean invoiceCalculationBean;
    @Inject
    protected InvoiceBackportBean invoiceBackportBean;
    @Inject
    protected InvoiceSnapshotService invoiceSnapshotService;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Events events;
    @Inject
    protected Dialogs dialogs;
    @Inject
    protected Notifications notifications;
    @Inject
    protected SnapshotConfig snapshotConfig;

    @Inject
    protected CollectionLoader<InvoiceLine> invoiceLinesDl;

    @Subscribe("snapshotBtn")
    protected void onSnapshotBtnClick(Button.ClickEvent event) {
        Date defaultStartDate = getPreviousMonth();
        Date defaultEndDate = getLastDayOfPreviousMonth();
        if (Boolean.TRUE.equals(snapshotConfig.getDateIntervalSelection())) {
            ScreenUtil.showDateIntervalSelectionDialog(this, messageBundle.getMessage("snapshotPeriodDialog.caption"),
                    inputDialogCloseEvent -> {
                        if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                            Date startDate = inputDialogCloseEvent.getValue("startDate");
                            Date endDate = inputDialogCloseEvent.getValue("endDate");
                            confirmAndRunSnapshot(startDate, endDate);
                        }
                    }, defaultStartDate, defaultEndDate);
        } else {
            confirmAndRunSnapshot(defaultStartDate, defaultEndDate);
        }
    }

    protected void confirmAndRunSnapshot(Date startDate, Date endDate) {
        if (invoiceSnapshotService.snapshotIsTaken(startDate, endDate)) {
            dialogs.createOptionDialog()
                    .withCaption(messageBundle.getMessage("snapshotConfirmation.caption"))
                    .withMessage(messageBundle.getMessage("snapshotConfirmation.message"))
                    .withActions(
                            new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                                invoiceSnapshotService.clearSnapshot(startDate, endDate);
                                runSnapshot(startDate, endDate);
                            }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        } else {
            runSnapshot(startDate, endDate);
        }
    }

    protected void runSnapshot(Date startDate, Date endDate) {
        Collection<ClosedTrade> trades = tradeService.getTrades(ClosedTrade.class, startDate, endDate,
                ViewBuilder.of(ClosedTrade.class)
                        .addView(View.LOCAL)
                        .build());
        if (trades.isEmpty()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withDescription(messageBundle.getMessage("snapshot.noData"))
                    .show();
            return;
        }
        BackgroundTask<Integer, Void> task = new SnapshotTask(trades);
        BackgroundWorkWindow.show(task, messageBundle.getMessage("snapshotTask.caption"), null, false);
    }

    @Install(to = "invoiceLinesTable.remove", subject = "afterActionPerformedHandler")
    protected void invoiceLinesTableRemoveAfterActionPerformedHandler(
            RemoveOperation.AfterActionPerformedEvent<InvoiceLine> afterActionPerformedEvent) {
        InvoiceLine invoiceLine = afterActionPerformedEvent.getItems().get(0);
        invoiceCalculationBean.recalculateInvoice(invoiceLine);
        invoiceBackportBean.backportPutOnInvoice(invoiceLine);
    }

    protected class SnapshotTask extends BackgroundTask<Integer, Void> {

        private Collection<ClosedTrade> trades;

        public SnapshotTask(Collection<ClosedTrade> trades) {
            super(10, TimeUnit.MINUTES, InvoiceLineBrowse.this);
            this.trades = trades;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) {
            invoiceSnapshotService.makeSnapshot(trades);
            return null;
        }

        @Override
        public void done(Void result) {
            events.publish(new InvoiceUpdatedEvent(this));
            invoiceLinesDl.load();
        }
    }
}