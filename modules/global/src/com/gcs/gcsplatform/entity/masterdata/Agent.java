package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.cuba.core.entity.BaseIntIdentityIdEntity;

@Table(name = "agents") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_Agent")
public class Agent extends BaseIntIdentityIdEntity {

    private static final long serialVersionUID = 3345582555019626972L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CPTY_ID")
    private Counterparty counterparty;

    @Column(name = "AGENT", length = 50)
    private String agentName;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}