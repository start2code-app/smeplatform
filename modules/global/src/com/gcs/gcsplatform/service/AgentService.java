package com.gcs.gcsplatform.service;

import java.util.List;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Agent;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.View;

public interface AgentService {

    String NAME = "gcsplatform_AgentService";

    /**
     * Gets agents associated with specified counterparty. If counterparty is null, returns all the agents.
     *
     * @param counterparty Counterparty
     * @param view         View
     * @return List of agents
     */
    List<Agent> getAgents(@Nullable Counterparty counterparty, View view);
}