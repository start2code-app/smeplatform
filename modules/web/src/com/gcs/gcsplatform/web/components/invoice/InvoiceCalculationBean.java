package com.gcs.gcsplatform.web.components.invoice;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.service.invoice.InvoiceService;
import com.haulmont.cuba.core.global.CommitContext;
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
     * <p>
     * Recalculates original invoice as well in case if such exists and it is not equals to actual one.
     * <p>
     * The case when original invoice should be recalculated is when invoice line's counterparty/currency has been
     * changed and specified invoice line is no longer belongs to the original invoice
     *
     * @param invoiceLine     Invoice line
     * @param originalInvoice Original invoice (before invoice line modifications)
     */
    public void recalculateOrCreateInvoice(InvoiceLine invoiceLine, Invoice originalInvoice) {
        Invoice actualInvoice = invoiceService.findInvoice(invoiceLine, ViewBuilder.of(Invoice.class)
                .addView(View.LOCAL)
                .build());
        if (actualInvoice != null) {
            actualInvoice = invoiceService.calculateAmount(actualInvoice);
        } else {
            actualInvoice = invoiceService.createInvoice(invoiceLine);
        }

        CommitContext commitContext = new CommitContext();
        commitContext.addInstanceToCommit(actualInvoice);

        if (originalInvoice != null && !originalInvoice.equals(actualInvoice)) {
            originalInvoice = invoiceService.calculateAmount(originalInvoice);
            commitContext.addInstanceToCommit(originalInvoice);
        }
        dataManager.commit(commitContext);
    }
}