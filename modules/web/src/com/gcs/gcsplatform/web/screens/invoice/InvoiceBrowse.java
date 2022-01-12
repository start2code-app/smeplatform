package com.gcs.gcsplatform.web.screens.invoice;

import java.util.Collection;
import java.util.Set;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;
import com.gcs.gcsplatform.service.invoice.InvoicePrintService;
import com.gcs.gcsplatform.service.invoice.InvoiceQuickBooksPublishService;
import com.gcs.gcsplatform.service.invoice.InvoiceWorkDocsPublishService;
import com.gcs.gcsplatform.web.events.InvoicePublishedEvent;
import com.gcs.gcsplatform.web.events.InvoiceUpdatedEvent;
import com.gcs.gcsplatform.web.task.InvoicePublishTask;
import com.gcs.gcsplatform.web.util.ScreenUtil;
import com.haulmont.cuba.gui.backgroundwork.BackgroundWorkProgressWindow;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.SplitPanel;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

@UiController("gcsplatform_Invoice.browse")
@UiDescriptor("invoice-browse.xml")
@LookupComponent("invoicesTable")
@LoadDataBeforeShow
public class InvoiceBrowse extends StandardLookup<Invoice> {

    @Inject
    protected GroupTable<Invoice> invoicesTable;
    @Inject
    protected SplitPanel invoicesSplit;

    @Inject
    protected InvoicePrintService printService;
    @Inject
    protected InvoiceWorkDocsPublishService workDocsPublishService;
    @Inject
    protected InvoiceQuickBooksPublishService quickBooksPublishService;
    @Inject
    protected MessageBundle messageBundle;

    @Inject
    protected CollectionContainer<Invoice> invoicesDc;
    @Inject
    protected CollectionLoader<Invoice> invoicesDl;
    @Inject
    protected CollectionContainer<InvoicePublishError> publishErrorsDc;

    @EventListener
    protected void invoiceUpdatedEventListener(InvoiceUpdatedEvent event) {
        invoicesDl.load();
    }

    @EventListener
    protected void invoicePublishedEventListener(InvoicePublishedEvent event) {
        Collection<InvoicePublishError> errors = event.getErrors();
        if (errors.isEmpty()) {
            return;
        }
        publishErrorsDc.setItems(errors);
        invoicesSplit.setSplitPosition(70);
    }

    @Subscribe("invoicesTable.print")
    protected void onInvoicesTablePrint(Action.ActionPerformedEvent event) {
        Set<Invoice> selected = invoicesTable.getSelected();
        for (Invoice invoice : selected) {
            printService.print(invoice);
        }
        invoicesDl.load();
    }

    @Subscribe("invoicesTable.workDocs")
    protected void onInvoicesTableWorkDocs(Action.ActionPerformedEvent event) {
        Set<Invoice> selected = invoicesTable.getSelected();
        invoicesSplit.setSplitPosition(100);
        publishErrorsDc.getMutableItems().clear();
        BackgroundTask<Integer, Collection<InvoicePublishError>> task = new InvoicePublishTask(selected,
                workDocsPublishService::publishToWorkDocs, this);
        BackgroundWorkProgressWindow.show(task, messageBundle.getMessage("publishWorkDocs.caption"), null,
                selected.size(), true, true);
    }

    @Subscribe("invoicesTable.quickBooks")
    protected void onInvoicesTableQuickBooks(Action.ActionPerformedEvent event) {
        Set<Invoice> selected = invoicesTable.getSelected();
        invoicesSplit.setSplitPosition(100);
        publishErrorsDc.getMutableItems().clear();
        BackgroundTask<Integer, Collection<InvoicePublishError>> task = new InvoicePublishTask(selected,
                quickBooksPublishService::publishToQB, this);
        BackgroundWorkProgressWindow.show(task, messageBundle.getMessage("publishQuickBooks.caption"), null,
                selected.size(), true, true);
    }

    @Install(to = "invoicesTable", subject = "styleProvider")
    protected String invoicesTableStyleProvider(Invoice entity, String property) {
        if (property == null) {
            if (Boolean.TRUE.equals(entity.getPostedToQB())) {
                return "v-table-row green-row";
            } else {
                return "v-table-row pink-row";
            }
        }
        return null;
    }

    @Install(to = "invoicesTable.xlsxFile", subject = "columnGenerator")
    protected Component invoicesTableXlsxFileColumnGenerator(Invoice invoice) {
        return ScreenUtil.generateDownloadLinkCell(invoice, "xlsxFile");
    }

    @Install(to = "invoicesTable.pdfFile", subject = "columnGenerator")
    protected Component invoicesTablePdfFileColumnGenerator(Invoice invoice) {
        return ScreenUtil.generateDownloadLinkCell(invoice, "pdfFile");
    }

    @Install(to = "invoicesTable.workDocs", subject = "enabledRule")
    protected boolean invoicesTableWorkDocsEnabledRule() {
        return isPrintedSelected();
    }

    @Install(to = "invoicesTable.quickBooks", subject = "enabledRule")
    protected boolean invoicesTableQuickBooksEnabledRule() {
        return isPrintedSelected();
    }

    protected boolean isPrintedSelected() {
        return invoicesTable.getSelected().stream()
                .anyMatch(Invoice::getPrinted);
    }

    public GroupTable<Invoice> getInvoicesTable() {
        return invoicesTable;
    }
}