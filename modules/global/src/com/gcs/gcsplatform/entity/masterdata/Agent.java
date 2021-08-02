package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@NamePattern("%s|agent")
@Table(name = "GCSPLATFORM_AGENT")
@Entity(name = "gcsplatform_Agent")
public class Agent extends StandardEntity {

    private static final long serialVersionUID = 3345582555019626972L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTERPARTY_ID")
    @NotNull
    private Counterparty counterparty;

    @Column(name = "AGENT", nullable = false, unique = true, length = 50)
    @NotNull
    private String agent;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}