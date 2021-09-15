package com.gcs.gcsplatform.web.screens.invoice;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.web.events.InvoiceLineUpdatedEvent;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

@UiController("gcsplatform_Invoice.browse")
@UiDescriptor("invoice-browse.xml")
@LookupComponent("invoicesTable")
@LoadDataBeforeShow
public class InvoiceBrowse extends StandardLookup<Invoice> {

    @Inject
    protected CollectionLoader<Invoice> invoicesDl;

    @EventListener
    protected void invoiceLineUpdatedEventListener(InvoiceLineUpdatedEvent event) {
        invoicesDl.load();
    }
}