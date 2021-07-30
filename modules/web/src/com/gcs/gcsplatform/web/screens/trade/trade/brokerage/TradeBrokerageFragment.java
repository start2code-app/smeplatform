package com.gcs.gcsplatform.web.screens.trade.trade.brokerage;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.trade.Trade;
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
    protected TextField<BigDecimal> BuybrokerageField;
    @Inject
    protected LookupPickerField<Category> categoryLookupPickerField;
    @Inject
    protected CheckBox brooverideCheckBox;
    @Inject
    protected CheckBox gcCheckBox;
    @Inject
    protected TextField<String> origtraderefField;
    @Inject
    protected TextField<BigDecimal> SellbrokerageField;
    @Inject
    protected CheckBox specialCheckBox;
    @Inject
    protected CheckBox subsCheckBox;

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
    }

    @Subscribe("subsCheckBox")
    protected void onSubsCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (subsCheckBox.isChecked()) {
            origtraderefField.setVisible(true);
            SellbrokerageField.setEnabled(false);
            SellbrokerageField.setValue(BigDecimal.ZERO);
            BuybrokerageField.setEnabled(false);
            BuybrokerageField.setValue(BigDecimal.ZERO);
            brooverideCheckBox.setValue(false);
        } else {
            origtraderefField.setVisible(false);
            updateBrokerage();
        }
    }

    protected void updateBrokerage() {
    }
}