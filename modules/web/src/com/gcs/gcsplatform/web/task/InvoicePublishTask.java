package com.gcs.gcsplatform.web.task;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.ErrorSeverity;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;
import com.gcs.gcsplatform.web.events.InvoicePublishedEvent;
import com.gcs.gcsplatform.web.events.InvoiceUpdatedEvent;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;
import com.haulmont.cuba.gui.screen.Screen;

public class InvoicePublishTask extends BackgroundTask<Integer, Collection<InvoicePublishError>> {

    private final Collection<Invoice> invoices;
    private final InvoicePublishProvider provider;

    public InvoicePublishTask(Collection<Invoice> invoices, InvoicePublishProvider provider, Screen screen) {
        super(10, TimeUnit.MINUTES, screen);
        this.invoices = invoices;
        this.provider = provider;
    }

    @Override
    public Collection<InvoicePublishError> run(TaskLifeCycle<Integer> taskLifeCycle) throws InterruptedException {
        int i = 0;
        List<InvoicePublishError> errors = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (taskLifeCycle.isCancelled()) {
                break;
            }

            InvoicePublishError error = provider.publish(invoice);
            if (error != null) {
                errors.add(error);
                if (error.getErrorType().getSeverity() == ErrorSeverity.FATAL) {
                    break;
                }
            }

            i++;
            taskLifeCycle.publish(i);
        }
        return errors;
    }

    @Override
    public void done(Collection<InvoicePublishError> result) {
        Events events = AppBeans.get(Events.class);
        events.publish(new InvoiceUpdatedEvent(this));
        events.publish(new InvoicePublishedEvent(this, result));
    }

    @FunctionalInterface
    public interface InvoicePublishProvider {

        InvoicePublishError publish(Invoice invoice);
    }
}
