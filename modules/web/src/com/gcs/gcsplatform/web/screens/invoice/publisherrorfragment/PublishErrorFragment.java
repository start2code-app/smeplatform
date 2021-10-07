package com.gcs.gcsplatform.web.screens.invoice.publisherrorfragment;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.publisherror.ErrorType;
import com.gcs.gcsplatform.entity.invoice.publisherror.InvoicePublishError;
import com.gcs.gcsplatform.web.components.qb.QuickBooksConnectorBean;
import com.gcs.gcsplatform.web.screens.invoice.InvoiceBrowse;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.AbstractAction;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LinkButton;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_PublishErrorFragment")
@UiDescriptor("publish-error-fragment.xml")
public class PublishErrorFragment extends ScreenFragment {

    @Inject
    protected HBoxLayout successBox;
    @Inject
    protected VBoxLayout errorBox;

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected Messages messages;
    @Inject
    protected QuickBooksConnectorBean quickBooksConnectorBean;

    @Inject
    protected CollectionContainer<InvoicePublishError> publishErrorsDc;

    @Subscribe(id = "publishErrorsDc", target = Target.DATA_CONTAINER)
    protected void onPublishErrorsDcCollectionChange(
            CollectionContainer.CollectionChangeEvent<InvoicePublishError> event) {
        boolean errorsEmpty = publishErrorsDc.getItems().isEmpty();
        successBox.setVisible(errorsEmpty);
        errorBox.setVisible(!errorsEmpty);
    }

    @Install(to = "publishErrorsTable.invoice", subject = "columnGenerator")
    protected Component publishErrorsTableInvoiceColumnGenerator(InvoicePublishError invoicePublishError) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        final Invoice invoice = invoicePublishError.getInvoice();
        String invoiceNumber = invoice.getInvoiceNumber();
        linkButton.setCaption(invoiceNumber);
        linkButton.setAction(new AbstractAction() {
            @Override
            public void actionPerform(Component component) {
                InvoiceBrowse invoiceBrowse = (InvoiceBrowse) getHostScreen();
                invoiceBrowse.getInvoicesTable().setSelected(invoice);
            }
        });
        return linkButton;
    }

    @Install(to = "publishErrorsTable.errorType", subject = "columnGenerator")
    protected Component publishErrorsTableErrorTypeColumnGenerator(InvoicePublishError invoicePublishError) {
        Label label = uiComponents.create(Label.class);
        ErrorType errorType = invoicePublishError.getErrorType();
        label.setValue(messages.getMessage(errorType));
        if (errorType == ErrorType.QUICKBOOKS_TOKEN_REFRESH_FAILED) {
            LinkButton linkButton = uiComponents.create(LinkButton.class);
            linkButton.setCaption(messages.getMainMessage("menu_config.quickBooksAuth"));
            linkButton.setAction(new AbstractAction() {
                @Override
                public void actionPerform(Component component) {
                    quickBooksConnectorBean.openOAuth2Page();
                }
            });
            VBoxLayout vBoxLayout = uiComponents.create(VBoxLayout.class);
            vBoxLayout.setSpacing(true);
            vBoxLayout.add(label);
            vBoxLayout.add(linkButton);
            return vBoxLayout;
        }
        return label;
    }
}