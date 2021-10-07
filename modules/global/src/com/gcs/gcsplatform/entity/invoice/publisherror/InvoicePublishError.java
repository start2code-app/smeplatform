package com.gcs.gcsplatform.entity.invoice.publisherror;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;

@MetaClass(name = "gcsplatform_InvoicePublishError")
public class InvoicePublishError extends BaseUuidEntity {

    private static final long serialVersionUID = -1418797990220391450L;

    @MetaProperty
    private Invoice invoice;

    @MetaProperty
    private String errorType;

    public static InvoicePublishError of(Invoice invoice, ErrorType errorType) {
        Metadata metadata = AppBeans.get(Metadata.class);
        InvoicePublishError error = metadata.create(InvoicePublishError.class);
        error.setInvoice(invoice);
        error.setErrorType(errorType);
        return error;
    }

    public ErrorType getErrorType() {
        return errorType == null ? null : ErrorType.fromId(errorType);
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType == null ? null : errorType.getId();
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}