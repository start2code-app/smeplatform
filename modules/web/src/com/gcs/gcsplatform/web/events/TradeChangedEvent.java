package com.gcs.gcsplatform.web.events;

import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class TradeChangedEvent extends ApplicationEvent implements UiEvent {

    public TradeChangedEvent(Object source) {
        super(source);
    }
}
