package com.gcs.gcsplatform.web.screens.invoice;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.invoice.Invoice;

@UiController("gcsplatform_Invoice.browse")
@UiDescriptor("invoice-browse.xml")
@LookupComponent("invoicesTable")
@LoadDataBeforeShow
public class InvoiceBrowse extends StandardLookup<Invoice> {
}