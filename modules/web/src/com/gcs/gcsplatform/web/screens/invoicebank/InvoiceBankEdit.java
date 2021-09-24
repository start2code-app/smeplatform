package com.gcs.gcsplatform.web.screens.invoicebank;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.InvoiceBank;

@UiController("gcsplatform_InvoiceBank.edit")
@UiDescriptor("invoice-bank-edit.xml")
@EditedEntityContainer("invoiceBankDc")
@LoadDataBeforeShow
public class InvoiceBankEdit extends StandardEditor<InvoiceBank> {
}