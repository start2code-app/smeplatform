package com.gcs.gcsplatform.web.screens.trade.trade.brokerage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.service.CounterpartyBrokerageService;
import com.gcs.gcsplatform.service.CounterpartyService;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_TradeBrokerageFragment")
@UiDescriptor("trade-brokerage-fragment.xml")
public class TradeBrokerageFragment extends ScreenFragment {

    @Inject
    protected CounterpartyBrokerageService counterpartyBrokerageService;
    @Inject
    protected CounterpartyService counterpartyService;

    @Inject
    protected TextField<BigDecimal> buyBrokerageField;
    @Inject
    protected LookupPickerField<Category> categoryLookupPickerField;
    @Inject
    protected CheckBox broOverideCheckBox;
    @Inject
    protected CheckBox gcCheckBox;
    @Inject
    protected TextField<String> origtraderefField;
    @Inject
    protected TextField<BigDecimal> sellBrokerageField;
    @Inject
    protected CheckBox specialCheckBox;
    @Inject
    protected CheckBox subsCheckBox;
    @Inject
    protected CheckBox subThirtyCheckBox;
    @Inject
    protected CheckBox moreThanThirtyCheckBox;

    @Inject
    protected InstanceContainer<Trade> tradeDc;
    @Inject
    protected CollectionLoader<Category> categoryDl;

    @Subscribe
    protected void onInit(InitEvent event) {
        categoryDl.load();
    }

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        Trade trade = tradeDc.getItem();
        initFieldValueToStringPropertyMapping(categoryLookupPickerField, trade, "category", "category");
        
        if (Boolean.TRUE.equals(trade.getBrooveride())) {
            buyBrokerageField.setEditable(true);
            sellBrokerageField.setEditable(true);
        }
    }

    @Subscribe("subsCheckBox")
    protected void onSubsCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (subsCheckBox.isChecked()) {
            origtraderefField.setVisible(true);
            sellBrokerageField.setEditable(false);
            sellBrokerageField.setValue(BigDecimal.ZERO);
            buyBrokerageField.setEditable(false);
            buyBrokerageField.setValue(BigDecimal.ZERO);
            broOverideCheckBox.setValue(false);
        } else {
            origtraderefField.setVisible(false);
            updateBrokerage();
        }
    }

    @Subscribe("broOverideCheckBox")
    protected void onbroOverideCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (Boolean.TRUE.equals(event.getValue())) {
            sellBrokerageField.setEditable(true);
            buyBrokerageField.setEditable(true);
            subsCheckBox.setValue(false);
        } else {
            sellBrokerageField.setEditable(false);
            buyBrokerageField.setEditable(false);
        }
        updateBrokerage();
    }

    @Subscribe("gcCheckBox")
    protected void onGcCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (Boolean.TRUE.equals(event.getValue())) {
            specialCheckBox.setValue(false);
            subThirtyCheckBox.setValue(false);
            moreThanThirtyCheckBox.setValue(false);
            updateBrokerage();
        }
    }

    @Subscribe("specialCheckBox")
    protected void onSpecialCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (Boolean.TRUE.equals(event.getValue())) {
            gcCheckBox.setValue(false);
            subThirtyCheckBox.setValue(false);
            moreThanThirtyCheckBox.setValue(false);
            updateBrokerage();
        }
    }

    @Subscribe("subThirtyCheckBox")
    protected void onSubThirtyCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (Boolean.TRUE.equals(event.getValue())) {
            specialCheckBox.setValue(false);
            gcCheckBox.setValue(false);
            moreThanThirtyCheckBox.setValue(false);
            updateBrokerage();
        }
    }

    @Subscribe("moreThanThirtyCheckBox")
    protected void onMoreThanThirtyCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (Boolean.TRUE.equals(event.getValue())) {
            specialCheckBox.setValue(false);
            subThirtyCheckBox.setValue(false);
            gcCheckBox.setValue(false);
            updateBrokerage();
        }
    }

    protected void updateBrokerage() {
        if (broOverideCheckBox.isChecked() || subsCheckBox.isChecked()) {
            return;
        }

        Trade trade = tradeDc.getItem();
        Category category = categoryLookupPickerField.getValue();

        Counterparty buyerCounterparty = counterpartyService.findCounterparty(trade.getBuyer());
        CounterpartyBrokerage buyerBrokerage = counterpartyBrokerageService.findCounterpartyBrokerage(buyerCounterparty,
                category, trade.getBrokerageType());
        if (buyerBrokerage != null) {
            buyBrokerageField.setValue(buyerBrokerage.getBrokerageValue());
        }

        Counterparty sellerCounterparty = counterpartyService.findCounterparty(trade.getSeller());
        CounterpartyBrokerage sellerBrokerage = counterpartyBrokerageService.findCounterpartyBrokerage(sellerCounterparty,
                category, trade.getBrokerageType());
        if (sellerBrokerage != null) {
            sellBrokerageField.setValue(sellerBrokerage.getBrokerageValue());
        }
    }
}