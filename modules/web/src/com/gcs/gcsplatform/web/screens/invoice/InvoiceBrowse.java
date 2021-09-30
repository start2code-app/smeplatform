package com.gcs.gcsplatform.web.screens.invoice;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.service.invoice.InvoicePrintService;
import com.gcs.gcsplatform.service.invoice.InvoicePublishService;
import com.gcs.gcsplatform.web.events.InvoiceLineUpdatedEvent;
import com.gcs.gcsplatform.web.util.ScreenUtil;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
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
    protected InvoicePrintService invoicePrintService;
    @Inject
    protected InvoicePublishService invoicePublishService;

    @Inject
    protected CollectionContainer<Invoice> invoicesDc;
    @Inject
    protected CollectionLoader<Invoice> invoicesDl;

    @EventListener
    protected void invoiceLineUpdatedEventListener(InvoiceLineUpdatedEvent event) {
        invoicesDl.load();
    }

    @Subscribe("invoicesTable.print")
    protected void onInvoicesTablePrint(Action.ActionPerformedEvent event) {
        invoicePrintService.print(invoicesTable.getSelected());
        invoicesDl.load();
    }

    @Subscribe("invoicesTable.workDocs")
    protected void onInvoicesTableWorkDocs(Action.ActionPerformedEvent event) {
        invoicePublishService.publishToWorkDocs(invoicesTable.getSelected());
        invoicesDl.load();
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
        return invoicesTable.getSelected().stream()
                .anyMatch(Invoice::getPrinted);
    }
}