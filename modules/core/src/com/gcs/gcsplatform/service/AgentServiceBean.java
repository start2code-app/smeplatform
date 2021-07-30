package com.gcs.gcsplatform.service;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.masterdata.Agent;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.queryconditions.JpqlCondition;
import org.springframework.stereotype.Service;

@Service(AgentService.NAME)
public class AgentServiceBean implements AgentService {

    @Inject
    private DataManager dataManager;

    @Override
    public List<Agent> getAgents(@Nullable Counterparty counterparty, View view) {
        FluentLoader.ByQuery<Agent, UUID> query = dataManager.load(Agent.class)
                .query("select e from gcsplatform_Agent e")
                .view(view);
        if (counterparty != null) {
            query.condition(new JpqlCondition("e.counterparty = :counterparty"));
            query.parameter("counterparty", counterparty);
        }
        return query.list();
    }
}