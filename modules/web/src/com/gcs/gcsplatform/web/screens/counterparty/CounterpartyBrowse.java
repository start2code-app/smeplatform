package com.gcs.gcsplatform.web.screens.counterparty;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.queryconditions.JpqlCondition;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;

@UiController("gcsplatform_Counterparty.browse")
@UiDescriptor("counterparty-browse.xml")
@LookupComponent("counterpartiesTable")
@LoadDataBeforeShow
public class CounterpartyBrowse extends StandardLookup<Counterparty> {

    protected boolean onlyActive;

    @Inject
    protected CollectionLoader<Counterparty> counterpartiesDl;

    public void setOnlyActive(boolean onlyActive) {
        this.onlyActive = onlyActive;
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        if (onlyActive) {
            counterpartiesDl.setCondition(new JpqlCondition("e.active = true"));
        }
    }
}