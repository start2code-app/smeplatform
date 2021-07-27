package com.gcs.gcsplatform.web.screens.agent;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Agent;

@UiController("gcsplatform_Agent.browse")
@UiDescriptor("agent-browse.xml")
@LookupComponent("agentsTable")
@LoadDataBeforeShow
public class AgentBrowse extends StandardLookup<Agent> {
}