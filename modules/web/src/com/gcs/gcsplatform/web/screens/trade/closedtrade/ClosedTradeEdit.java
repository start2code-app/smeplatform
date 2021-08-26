package com.gcs.gcsplatform.web.screens.trade.closedtrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_ClosedTrade.edit")
@UiDescriptor("closed-trade-edit.xml")
public class ClosedTradeEdit extends TradeEdit<OpenedTrade> {

    @Inject
    protected DateField<Date> maturityDateField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        maturityDateField.setEditable(true);
    }
}