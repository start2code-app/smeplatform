package com.gcs.gcsplatform.web.screens.invoicecompany;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.InvoiceCompany;

@UiController("gcsplatform_InvoiceCompany.edit")
@UiDescriptor("invoice-company-edit.xml")
@EditedEntityContainer("invoiceCompanyDc")
@LoadDataBeforeShow
public class InvoiceCompanyEdit extends StandardEditor<InvoiceCompany> {
}