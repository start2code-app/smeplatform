package com.gcs.gcsplatform.web.screens.invoicebank;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.InvoiceBank;

@UiController("gcsplatform_InvoiceBank.browse")
@UiDescriptor("invoice-bank-browse.xml")
@LookupComponent("invoiceBanksTable")
@LoadDataBeforeShow
public class InvoiceBankBrowse extends StandardLookup<InvoiceBank> {
}