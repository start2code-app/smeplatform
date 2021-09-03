package com.gcs.gcsplatform.web.screens.invoiceline;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;

@UiController("gcsplatform_InvoiceLine.edit")
@UiDescriptor("invoice-line-edit.xml")
@EditedEntityContainer("invoiceLineDc")
@LoadDataBeforeShow
public class InvoiceLineEdit extends StandardEditor<InvoiceLine> {
}