package com.gcs.gcsplatform.web.screens.trade.livetrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.edit")
@UiDescriptor("live-trade-edit.xml")
public class LiveTradeEdit extends TradeEdit<LiveTrade> {

    @Inject
    private DateField<Date> maturityDateField;

    @Subscribe
    public void onAfterShow1(AfterShowEvent event) {

        maturityDateField.setEditable(true);

    }



}