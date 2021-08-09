package com.gcs.gcsplatform.entity.pnl;

import java.math.BigDecimal;
import javax.persistence.Transient;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;

@MetaClass(name = "gcsplatform_Pnl")
public class Pnl extends StandardEntity {

    private static final long serialVersionUID = -2823171593611076391L;

    @Transient
    @MetaProperty
    private String broker;

    @Transient
    @MetaProperty
    private String counterparty;

    @Transient
    @MetaProperty
    private String tradeCurrency;

    @Transient
    @MetaProperty
    private BigDecimal pnl;

    @Transient
    @MetaProperty
    private BigDecimal gbpEquivalent;

    public BigDecimal getGbpEquivalent() {
        return gbpEquivalent;
    }

    public void setGbpEquivalent(BigDecimal gbpEquivalent) {
        this.gbpEquivalent = gbpEquivalent;
    }

    public BigDecimal getPnl() {
        return pnl;
    }

    public void setPnl(BigDecimal pnl) {
        this.pnl = pnl;
    }

    public String getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}