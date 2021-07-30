package com.gcs.gcsplatform.web.screens.trade.trade;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.Trade;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_TradeFragment")
@UiDescriptor("trade-fragment.xml")
public class TradeFragment extends ScreenFragment {

    @Inject
    protected InstanceContainer<Trade> tradeDc;

    public void setTrade(Trade trade) {
        tradeDc.setItem(trade);
    }
}