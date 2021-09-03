package com.gcs.gcsplatform.web.screens.invoiceline;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.InvoiceService;
import com.gcs.gcsplatform.service.TradeService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.backgroundwork.BackgroundWorkProgressWindow;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getFirstDayOfMonth;
import static com.gcs.gcsplatform.util.DateUtils.getLastDayOfMonth;

@UiController("gcsplatform_InvoiceLine.browse")
@UiDescriptor("invoice-line-browse.xml")
@LookupComponent("invoiceLinesTable")
@LoadDataBeforeShow
public class InvoiceLineBrowse extends StandardLookup<InvoiceLine> {

    @Inject
    protected TradeService tradeService;
    @Inject
    protected InvoiceService invoiceService;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected MessageBundle messageBundle;

    @Inject
    protected CollectionLoader<InvoiceLine> invoiceLinesDl;

    @Subscribe("snapshotBtn")
    protected void onSnapshotBtnClick(Button.ClickEvent event) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        Date previousMonth = calendar.getTime();
        Collection<ClosedTrade> trades = tradeService.getTrades(ClosedTrade.class, ViewBuilder.of(ClosedTrade.class)
                        .addView(View.LOCAL)
                        .build(),
                getFirstDayOfMonth(previousMonth),
                getLastDayOfMonth(previousMonth));
        if (trades.isEmpty()) {
            return;
        }
        BackgroundTask<Integer, Void> task = new SnapshotTask(trades);
        BackgroundWorkProgressWindow.show(task, messageBundle.getMessage("snapshotTask.caption"),
                messageBundle.getMessage("snapshotTask.description"), trades.size() + 1, true, true);
    }

    protected class SnapshotTask extends BackgroundTask<Integer, Void> {

        private Collection<ClosedTrade> trades;

        public SnapshotTask(Collection<ClosedTrade> trades) {
            super(10, TimeUnit.MINUTES, InvoiceLineBrowse.this);
            this.trades = trades;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            int i = 0;
            CommitContext commitContext = new CommitContext();
            for (ClosedTrade trade : trades) {
                if (taskLifeCycle.isCancelled()) {
                    break;
                }
                Collection<InvoiceLine> invoiceLines = invoiceService.splitTrade(trade);
                for (InvoiceLine invoiceLine : invoiceLines) {
                    commitContext.addInstanceToCommit(invoiceLine);
                }
                i++;
                taskLifeCycle.publish(i);
            }
            dataManager.commit(commitContext);
            return null;
        }

        @Override
        public void done(Void result) {
            invoiceLinesDl.load();
        }
    }
}