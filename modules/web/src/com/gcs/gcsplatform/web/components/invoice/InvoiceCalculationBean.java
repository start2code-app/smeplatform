package com.gcs.gcsplatform.web.components.invoice;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.service.invoice.InvoiceService;
import com.haulmont.cuba.core.global.DataManager;
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

    /**
     * Searches for a corresponding invoice and recalculates pnl/gbp amount by selecting all of the related invoice
     * lines.
     * <p>
     * If no such invoice exists, creates a new one and sets pnl/gbp amount directly from specified invoice line.
     *
     * @param invoiceLine Invoice line
     */
    public void recalculateOrCreateInvoice(InvoiceLine invoiceLine) {
        Invoice invoice = invoiceService.findInvoice(invoiceLine, ViewBuilder.of(Invoice.class)
                .addView(View.LOCAL)
                .build());
        if (invoice != null) {
            invoice = invoiceService.calculateAmount(invoice);
        } else {
            invoice = invoiceService.createInvoice(invoiceLine);
        }
        dataManager.commit(invoice);
    }
}