package com.gcs.gcsplatform.web.screens.pnl;

import java.util.Collection;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("gcsplatform_PnlChartScreen")
@UiDescriptor("pnl-chart-screen.xml")
public class PnlChartScreen extends Screen {

    @Inject
    protected GroupTable<Pnl> pnlByBrokerTable;
    @Inject
    protected GroupTable<Pnl> pnlByCounterpartyTable;

    @Inject
    protected CollectionContainer<Pnl> pnlByBrokerDc;
    @Inject
    protected CollectionContainer<Pnl> pnlByCounterpartyDc;

    public void setPnlByBroker(Collection<Pnl> pnlByBrokerCollection) {
        pnlByBrokerDc.setItems(pnlByBrokerCollection);
    }

    public void setPnlByCounterparty(Collection<Pnl> pnlByCounterparty) {
        pnlByCounterpartyDc.setItems(pnlByCounterparty);
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        pnlByBrokerTable.expandAll();
        pnlByCounterpartyTable.expandAll();
    }
}