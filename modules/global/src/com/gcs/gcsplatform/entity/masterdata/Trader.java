package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gcs.gcsplatform.entity.ExtStandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "GCSPLATFORM_TRADER", indexes = {
        @Index(name = "IDX_GCSPLATFORM_TRADER_UNIQ_NAME", columnList = "NAME, DELETE_TS_NN", unique = true)
})
@Entity(name = "gcsplatform_Trader")
public class Trader extends ExtStandardEntity {

    private static final long serialVersionUID = 3345582555019626972L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTERPARTY_ID")
    @NotNull
    private Counterparty counterparty;

    @Column(name = "NAME", nullable = false, length = 30)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}