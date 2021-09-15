package com.gcs.gcsplatform.web.events;

import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class InvoiceLineUpdatedEvent extends ApplicationEvent implements UiEvent {

    public InvoiceLineUpdatedEvent(Object source) {
        super(source);
    }
}
