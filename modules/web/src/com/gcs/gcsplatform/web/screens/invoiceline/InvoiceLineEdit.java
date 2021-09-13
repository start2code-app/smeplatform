package com.gcs.gcsplatform.web.screens.invoiceline;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.service.invoice.InvoiceService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
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
    protected DataManager dataManager;
    @Inject
    protected InvoiceService invoiceService;

    @Inject
    protected InstanceContainer<InvoiceLine> invoiceLineDc;

    // todo: numdays calculation on value/maturity date change. validations
    // todo: pnl/gbp calculation

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
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPostCommit(DataContext.PostCommitEvent event) {
        CommitContext commitContext = new CommitContext();
        recalculateOrCreateInvoice(commitContext);
        backportChangesToTrade(commitContext);
        dataManager.commit(commitContext);
    }

    protected void recalculateOrCreateInvoice(CommitContext commitContext) {
        Invoice invoice = invoiceService.findInvoice(getEditedEntity(), ViewBuilder.of(Invoice.class)
                .addView(View.LOCAL)
                .build());
        if (invoice != null) {
            invoice = invoiceService.calculateAmount(invoice);
        } else {
            invoice = invoiceService.createInvoice(getEditedEntity());
        }
        commitContext.addInstanceToCommit(invoice);
    }

    protected void backportChangesToTrade(CommitContext commitContext) {
        InvoiceLine invoiceLine = getEditedEntity();
        ClosedTrade trade = invoiceLine.getTrade();
        trade.setBrokerage(invoiceLine.getBrokerage(), invoiceLine.getTradeSide());
        trade.setCounterparty(invoiceLine.getCounterparty(), invoiceLine.getTradeSide());
        trade.setInvoiceCode(invoiceLine.getLocation(), invoiceLine.getTradeSide());
        trade.setCounterpartyCode(invoiceLine.getCounterpartyCode(), invoiceLine.getTradeSide());
        trade.setTradeCurrency(invoiceLine.getCurrency());
        trade.setMaturityDate(invoiceLine.getMaturityDate());
        trade.setValueDate(invoiceLine.getValueDate());
        trade.setNominal(invoiceLine.getNominal());
        trade.setNotes(invoiceLine.getNotes());
        commitContext.addInstanceToCommit(trade);
    }
}