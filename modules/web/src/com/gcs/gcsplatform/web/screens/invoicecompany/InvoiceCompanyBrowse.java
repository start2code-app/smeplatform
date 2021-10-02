package com.gcs.gcsplatform.web.screens.invoicecompany;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.InvoiceCompany;

@UiController("gcsplatform_InvoiceCompany.browse")
@UiDescriptor("invoice-company-browse.xml")
@LookupComponent("invoiceCompaniesTable")
@LoadDataBeforeShow
public class InvoiceCompanyBrowse extends StandardLookup<InvoiceCompany> {
}