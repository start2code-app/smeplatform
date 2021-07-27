package com.gcs.gcsplatform.web.screens.agent;

import com.haulmont.cuba.gui.screen.*;
import com.gcs.gcsplatform.entity.masterdata.Agent;

@UiController("gcsplatform_Agent.edit")
@UiDescriptor("agent-edit.xml")
@EditedEntityContainer("agentDc")
@LoadDataBeforeShow
public class AgentEdit extends StandardEditor<Agent> {
}