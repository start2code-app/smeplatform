package com.gcs.gcsplatform.web.screens.invoiceline;

import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.web.components.BrokerageBean;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.gcs.gcsplatform.web.components.invoice.InvoiceBackportBean;
import com.gcs.gcsplatform.web.components.invoice.InvoiceCalculationBean;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_InvoiceLine.edit")
@UiDescriptor("invoice-line-edit.xml")
@EditedEntityContainer("invoiceLineDc")
@LoadDataBeforeShow
public class InvoiceLineEdit extends StandardEditor<InvoiceLine> {

    @Inject
    protected LookupPickerField<Counterparty> counterpartyLookupPickerField;
    @Inject
    protected LookupPickerField<Currency> currencyLookupPickerField;

    @Inject
    protected BrokerageBean brokerageBean;
    @Inject
    protected PnlCalculationBean pnlCalculationBean;
    @Inject
    protected InvoiceBackportBean invoiceBackportBean;
    @Inject
    protected InvoiceCalculationBean invoiceCalculationBean;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;

    @Inject
    protected InstanceContainer<InvoiceLine> invoiceLineDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(currencyLookupPickerField, invoiceLineDc, "currency", "currency");
        initFieldValueToStringPropertyMapping(counterpartyLookupPickerField, invoiceLineDc, "counterparty",
                "counterparty");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        counterpartyLookupPickerField.addValueChangeListener(this::onCounterpartyLookupPickerFieldValueChange);
    }

    protected void onCounterpartyLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Counterparty> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        InvoiceLine invoiceLine = getEditedEntity();
        Counterparty counterparty = event.getValue();
        invoiceLine.setCounterpartyCode(counterparty != null ? counterparty.getBillingInfo1() : null);
        invoiceLine.setLocation(counterparty != null ? counterparty.getBillingInfo3() : null);
        invoiceLine.setBuyerOrSeller(counterparty != null ? counterparty.getCounterparty() : null);
        brokerageBean.updateBrokerage(invoiceLine);
        pnlCalculationBean.updatePnl(getEditedEntity(), invoiceLine.getFx());
    }

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
        pnlCalculationBean.updatePnl(invoiceLine, invoiceLine.getFx());
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
        pnlCalculationBean.updatePnl(invoiceLine, invoiceLine.getFx());
    }

    @Subscribe("brokerageField")
    protected void onBrokerageFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        pnlCalculationBean.updatePnl(getEditedEntity(), getEditedEntity().getFx());
    }

    @Subscribe("nominalField")
    protected void onNominalFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        pnlCalculationBean.updatePnl(getEditedEntity(), getEditedEntity().getFx());
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        invoiceBackportBean.backportChangesToTrade(getEditedEntity());
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPostCommit(DataContext.PostCommitEvent event) {
        invoiceCalculationBean.recalculateOrCreateInvoice(getEditedEntity());
    }
}