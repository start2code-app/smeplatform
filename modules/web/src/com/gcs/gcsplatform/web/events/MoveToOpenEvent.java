package com.gcs.gcsplatform.web.events;

import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class MoveToOpenEvent extends ApplicationEvent implements UiEvent {

    public MoveToOpenEvent(Object source) {
        super(source);
    }
}
