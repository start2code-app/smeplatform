package com.gcs.gcsplatform.web.screens.invoiceline;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.web.components.invoice.InvoiceBackportBean;
import com.gcs.gcsplatform.web.components.invoice.InvoiceCalculationBean;
import com.gcs.gcsplatform.web.components.pnl.PnlCalculationBean;
import com.gcs.gcsplatform.web.screens.invoiceline.brokerageselection.BrokerageSelectionFragment;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.PopupButton;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.ScreenValidation;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.util.DateUtils.getDaysBetweenDates;

@UiController("gcsplatform_InvoiceLine.edit")
@UiDescriptor("invoice-line-edit.xml")
@EditedEntityContainer("invoiceLineDc")
@LoadDataBeforeShow
public class InvoiceLineEdit extends StandardEditor<InvoiceLine> {

    @Inject
    protected PopupButton brokeragePopupButton;
    @Inject
    protected BrokerageSelectionFragment brokerageSelectionFragment;

    @Inject
    protected PnlCalculationBean pnlCalculationBean;
    @Inject
    protected InvoiceBackportBean invoiceBackportBean;
    @Inject
    protected InvoiceCalculationBean invoiceCalculationBean;
    @Inject
    protected ScreenValidation screenValidation;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;

    @Subscribe("valueDateField")
    protected void onValueDateFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        Date value = event.getValue();
        Date prevValue = event.getPrevValue();
        InvoiceLine invoiceLine = getEditedEntity();
        Date maturityDate = invoiceLine.getMaturityDate();

        if (value != null && maturityDate != null && value.after(maturityDate)) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withDescription(messageBundle.getMessage("valueDate.validationMsg"))
                    .show();
            invoiceLine.setValueDate(prevValue);
            return;
        }
        updateNumDays();
    }

    @Subscribe("maturityDateField")
    protected void onMaturityDateFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (!event.isUserOriginated()) {
            return;
        }

        Date value = event.getValue();
        Date prevValue = event.getPrevValue();
        InvoiceLine invoiceLine = getEditedEntity();
        Date valueDate = invoiceLine.getValueDate();

        if (value != null && valueDate != null && value.before(valueDate)) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withDescription(messageBundle.getMessage("maturityDate.validationMsg"))
                    .show();
            invoiceLine.setMaturityDate(prevValue);
            return;
        }
        updateNumDays();
    }

    protected void updateNumDays() {
        InvoiceLine invoiceLine = getEditedEntity();
        invoiceLine.setNumdays(getDaysBetweenDates(invoiceLine.getMaturityDate(), invoiceLine.getValueDate()));
    }

    @Subscribe(id = "invoiceLineDc", target = Target.DATA_CONTAINER)
    protected void onInvoiceLineDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<InvoiceLine> event) {
        String property = event.getProperty();
        if (property.equals("nominal")
                || property.equals("brokerage")
                || property.equals("numdays")) {
            pnlCalculationBean.updatePnl(getEditedEntity());
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        invoiceBackportBean.backportChangesToTrade(getEditedEntity());
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPostCommit(DataContext.PostCommitEvent event) {
        invoiceCalculationBean.recalculateInvoice(getEditedEntity());
    }

    @Subscribe("savePopupBtn")
    protected void onSavePopupBtnClick(Button.ClickEvent event) {
        ValidationErrors validationErrors = screenValidation.validateUiComponents(
                brokerageSelectionFragment.getFragment());
        if (validationErrors.isEmpty()) {
            invoiceBackportBean.backportBrokerage(getEditedEntity(),
                    brokerageSelectionFragment.getBrokerage(),
                    brokerageSelectionFragment.getCategory(),
                    brokerageSelectionFragment.getBrokerageType(),
                    brokerageSelectionFragment.getBroOverride());
            brokeragePopupButton.setPopupVisible(false);
        } else {
            screenValidation.showValidationErrors(this, validationErrors);
        }
    }

    @Subscribe("closePopupBtn")
    protected void onClosePopupBtnClick(Button.ClickEvent event) {
        brokeragePopupButton.setPopupVisible(false);
    }
}