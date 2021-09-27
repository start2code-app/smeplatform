package com.gcs.gcsplatform.web.screens.invoiceline.brokerageselection;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.service.BrokerageService;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.ScreenValidation;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_BrokerageSelectionFragment")
@UiDescriptor("brokerage-selection-fragment.xml")
public class BrokerageSelectionFragment extends ScreenFragment {

    @Inject
    protected TextField<BigDecimal> brokerageField;
    @Inject
    protected LookupField<BrokerageType> brokerageTypeField;
    @Inject
    protected CheckBox broOverrideCheckBox;
    @Inject
    protected LookupField<Category> categoryLookupField;

    @Inject
    protected BrokerageService brokerageService;

    @Inject
    protected InstanceContainer<InvoiceLine> invoiceLineDc;
    @Inject
    protected CollectionLoader<Category> categoryDl;

    @Subscribe
    protected void onInit(InitEvent event) {
        categoryDl.load();
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onAfterShowHost(Screen.AfterShowEvent event) {
        brokerageField.setValue(invoiceLineDc.getItem().getBrokerage());
    }

    @Subscribe("brokerageTypeField")
    protected void onBrokerageTypeFieldValueChange(HasValue.ValueChangeEvent<BrokerageType> event) {
        updateBrokerage();
    }

    @Subscribe("categoryLookupField")
    protected void onCategoryLookupFieldValueChange(HasValue.ValueChangeEvent<Category> event) {
        updateBrokerage();
    }

    @Subscribe("broOverrideCheckBox")
    protected void onBroOverrideCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        brokerageField.setEditable(Boolean.TRUE.equals(event.getValue()));
        updateBrokerage();
    }

    protected void updateBrokerage() {
        if (broOverrideCheckBox.isChecked()) {
            return;
        }
        String counterparty = invoiceLineDc.getItem().getCounterparty();
        String category = categoryLookupField.getValue() != null ? categoryLookupField.getValue().getCategory() : null;
        BrokerageType brokerageType = brokerageTypeField.getValue();
        BigDecimal brokerageValue = brokerageService.findBrokerageValue(counterparty, category, brokerageType);
        brokerageField.setValue(brokerageValue);
    }

    public String getCategory() {
        return categoryLookupField.getValue() != null ? categoryLookupField.getValue().getCategory() : null;
    }

    public BrokerageType getBrokerageType() {
        return brokerageTypeField.getValue();
    }

    public boolean getBroOverride() {
        return broOverrideCheckBox.isChecked();
    }

    public BigDecimal getBrokerage() {
        return brokerageField.getValue();
    }
}