package com.gcs.gcsplatform.web.screens.trade.livetrade;

import java.util.Date;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.web.screens.trade.TradeEdit;
import com.gcs.gcsplatform.web.screens.trade.brokeragefragment.TradeBrokerageFragment;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_LiveTrade.edit")
@UiDescriptor("live-trade-edit.xml")
public class LiveTradeEdit extends TradeEdit<LiveTrade> {

    @Inject
    private DateField<Date> maturityDateField;
    @Inject
    private CheckBox gmSla;
    @Inject
    private CollectionContainer<Currency> currencyDc;
    @Inject
    private TradeBrokerageFragment brokerageFragment;

    @Subscribe
    public void onAfterShow1(AfterShowEvent event) {

        if ( PersistenceHelper.isNew(tradeDc.getItem() ) )
        {
            brokerageFragment.getSubsCheckBox().setValue(true);
        }
        maturityDateField.setEditable(true);

    }



    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if ( PersistenceHelper.isNew(getEditedEntity())  ) {
            this.gmSla.setValue(false);

        }


    }

}