package com.gcs.gcsplatform.web.screens.trader;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.queryconditions.JpqlCondition;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Trader;

@UiController("gcsplatform_Trader.browse")
@UiDescriptor("trader-browse.xml")
@LookupComponent("tradersTable")
@LoadDataBeforeShow
public class TraderBrowse extends StandardLookup<Trader> {

    private Counterparty counterparty;

    @Inject
    protected CollectionLoader<Trader> tradersDl;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        if (counterparty != null) {
            tradersDl.setCondition(new JpqlCondition("e.counterparty = :counterparty"));
            tradersDl.setParameter("counterparty", counterparty);
        }
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}