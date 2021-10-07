package com.gcs.gcsplatform.web.events;

import java.util.Collection;

import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;
import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class InvoicePublishedEvent extends ApplicationEvent implements UiEvent {

    private final Collection<InvoicePublishError> errors;

    public InvoicePublishedEvent(Object source, Collection<InvoicePublishError> errors) {
        super(source);
        this.errors = errors;
    }

    public Collection<InvoicePublishError> getErrors() {
        return errors;
    }
}
