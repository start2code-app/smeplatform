package com.gcs.gcsplatform.web.events;

import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class LiveTradeUpdatedEvent extends ApplicationEvent implements UiEvent {

    public LiveTradeUpdatedEvent(Object source) {
        super(source);
    }
}
