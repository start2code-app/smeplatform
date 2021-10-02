package com.gcs.gcsplatform.web.events;

import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class InvoiceUpdatedEvent extends ApplicationEvent implements UiEvent {

    public InvoiceUpdatedEvent(Object source) {
        super(source);
    }
}
