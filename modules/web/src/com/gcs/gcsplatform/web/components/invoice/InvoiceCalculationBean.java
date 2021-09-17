package com.gcs.gcsplatform.web.components.invoice;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.service.invoice.InvoiceService;
import com.gcs.gcsplatform.web.events.InvoiceLineUpdatedEvent;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Component;

@Component(InvoiceCalculationBean.NAME)
public class InvoiceCalculationBean {

    public static final String NAME = "gcsplatform_InvoiceCalculationBean";

    @Inject
    private InvoiceService invoiceService;
    @Inject
    private DataManager dataManager;
    @Inject
    private Events events;

    /**
     * Searches for a corresponding invoice and recalculates its pnl/gbp amount by selecting all of the related invoice
     * lines.
     *
     * @param invoiceLine Invoice line
     */
    public void recalculateInvoice(InvoiceLine invoiceLine) {
        Invoice invoice = invoiceService.findInvoice(invoiceLine, ViewBuilder.of(Invoice.class)
                .addView(View.LOCAL)
                .build());
        if (invoice != null) {
            invoice = invoiceService.calculateAmount(invoice);
            dataManager.commit(invoice);
            events.publish(new InvoiceLineUpdatedEvent(this));
        }
    }
}