package com.gcs.gcsplatform.web.screens.agent;

import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Agent;

@UiController("gcsplatform_Agent.browse")
@UiDescriptor("agent-browse.xml")
@LookupComponent("agentsTable")
@LoadDataBeforeShow
public class AgentBrowse extends StandardLookup<Agent> {

    private Counterparty counterparty;

    @Inject
    protected CollectionLoader<Agent> agentsDl;

    @Subscribe
    protected void onInit(InitEvent event) {
        agentsDl.setParameter("counterparty", counterparty);
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}