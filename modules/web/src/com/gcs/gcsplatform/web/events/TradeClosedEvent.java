package com.gcs.gcsplatform.web.events;

import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class TradeClosedEvent extends ApplicationEvent implements UiEvent {

    public TradeClosedEvent(Object source) {
        super(source);
    }
}
