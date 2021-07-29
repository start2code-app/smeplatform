package com.gcs.gcsplatform.web.screens.trade.trade.brokerage;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.BrokerageCategory;
import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.gui.components.LookupPickerField;
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
    protected LookupPickerField<BrokerageCategory> brokerageCategoryLookupPickerField;
    @Inject
    protected InstanceContainer<Trade> tradeDc;

    @Subscribe
    protected void onAttach(AttachEvent event) {
        Trade trade = tradeDc.getItem();
        initFieldValueToStringPropertyMapping(brokerageCategoryLookupPickerField, trade, "category", "category");
    }
}