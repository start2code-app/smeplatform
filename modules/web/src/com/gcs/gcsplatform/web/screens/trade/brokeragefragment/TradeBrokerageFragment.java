package com.gcs.gcsplatform.web.screens.trade.brokeragefragment;

import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.gcs.gcsplatform.web.components.brokerage.BrokerageBean;
import com.gcs.gcsplatform.web.events.TradeClosedEvent;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.springframework.context.event.EventListener;

import static com.gcs.gcsplatform.web.util.ScreenUtil.initFieldValueToStringPropertyMapping;

@UiController("gcsplatform_TradeBrokerageFragment")
@UiDescriptor("trade-brokerage-fragment.xml")
public class TradeBrokerageFragment extends ScreenFragment {

    @Inject
    protected BrokerageBean brokerageBean;

    @Inject
    protected TextField<BigDecimal> buyBrokerageField;
    @Inject
    protected LookupPickerField<Category> categoryLookupPickerField;
    @Inject
    protected CheckBox broOverideCheckBox;
    @Inject
    protected TextField<String> origtraderefField;
    @Inject
    protected TextField<BigDecimal> sellBrokerageField;
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

    protected void onCategoryLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Category> event) {
        if (event.isUserOriginated()) {
            brokerageBean.updateBrokerage(tradeDc.getItem());
        }
    }

    @Subscribe("subsCheckBox")
    protected void onSubsCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }
        if (Boolean.TRUE.equals(event.getValue())) {
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

    @Subscribe("brokerageTypeField")
    protected void onBrokerageTypeFieldValueChange(HasValue.ValueChangeEvent<BrokerageType> event) {
        if (event.isUserOriginated()) {
            brokerageBean.updateBrokerage(tradeDc.getItem());
        }
    }

    @EventListener
    protected void onTradeClosed(TradeClosedEvent event) {
        origtraderefField.setVisible(Boolean.TRUE.equals(tradeDc.getItem().getSubs()));
    }
}