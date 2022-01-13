package com.gcs.gcsplatform.web.screens.trade.openedtrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_OpenedTrade.edit")
@UiDescriptor("opened-trade-edit.xml")
public class OpenedTradeEdit extends TradeEdit<OpenedTrade> {

    @Inject
    private TextField<Long> numdaysField;
    @Inject
    private DateField<Date> invoiceDateField;
    @Inject
    private DateField<Date> maturityDateField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        numdaysField.setVisible(false);
        invoiceDateField.setVisible(false);
        maturityDateField.setVisible(false);
    }
}