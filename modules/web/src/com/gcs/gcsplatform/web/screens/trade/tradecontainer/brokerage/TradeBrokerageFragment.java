package com.gcs.gcsplatform.web.screens.trade.tradecontainer.brokerage;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.BrokerageBean;
import com.gcs.gcsplatform.web.components.PnlCalculationBean;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_TradeBrokerageFragment")
@UiDescriptor("trade-brokerage-fragment.xml")
public class TradeBrokerageFragment extends ScreenFragment {

    @Inject
    protected BrokerageBean brokerageBean;
    @Inject
    protected PnlCalculationBean pnlCalculationBean;

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

    @Subscribe(target = Target.PARENT_CONTROLLER)
    protected void onAfterShowHost(Screen.AfterShowEvent event) {
        initFieldValueToStringPropertyMapping(categoryLookupPickerField, tradeDc, "category", "category");

        /*
         * Subscribe manually to preserve listeners execution order. First listener maps field value to entity.
         */
        categoryLookupPickerField.addValueChangeListener(this::onCategoryLookupPickerFieldValueChange);

        Trade trade = tradeDc.getItem();
        if (Boolean.TRUE.equals(trade.getBrooveride())) {
            buyBrokerageField.setEditable(true);
            sellBrokerageField.setEditable(true);
        }

        origtraderefField.setVisible(Boolean.TRUE.equals(trade.getSubs()));
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
            brokerageBean.updateBrokerage(tradeDc.getItem());
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
            origtraderefField.setVisible(false);
        } else {
            sellBrokerageField.setEditable(false);
            buyBrokerageField.setEditable(false);
            brokerageBean.updateBrokerage(tradeDc.getItem());
        }
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
        }
        brokerageBean.updateBrokerage(tradeDc.getItem());
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
        }
        brokerageBean.updateBrokerage(tradeDc.getItem());
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
        }
        brokerageBean.updateBrokerage(tradeDc.getItem());
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
        }
        brokerageBean.updateBrokerage(tradeDc.getItem());
    }

    protected void onCategoryLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Category> event) {
        if (event.isUserOriginated()) {
            brokerageBean.updateBrokerage(tradeDc.getItem());
        }
    }

    @Subscribe("sellBrokerageField")
    protected void onSellBrokerageFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        pnlCalculationBean.updatePnl(tradeDc.getItem());
    }

    @Subscribe("buyBrokerageField")
    protected void onBuyBrokerageFieldValueChange(HasValue.ValueChangeEvent<BigDecimal> event) {
        pnlCalculationBean.updatePnl(tradeDc.getItem());
    }
}