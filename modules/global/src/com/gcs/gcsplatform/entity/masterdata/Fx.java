package com.gcs.gcsplatform.entity.masterdata;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

@PublishEntityChangedEvents
@Table(name = "GCSPLATFORM_FX", indexes = {
        @Index(name = "IDX_GCSPLATFORM_FX_UNQ", columnList = "CURRENCY_ID, BILLING_DATE", unique = true)
})
@Entity(name = "gcsplatform_Fx")
public class Fx extends StandardEntity {

    private static final long serialVersionUID = 3579922802955412632L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @NotNull
    @Column(name = "FX_VALUE", nullable = false, precision = 10, scale = 4)
    private BigDecimal fxValue;

    @Temporal(TemporalType.DATE)
    @Column(name = "BILLING_DATE", nullable = false)
    @NotNull
    private Date billingDate;

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public BigDecimal getFxValue() {
        return fxValue;
    }

    public void setFxValue(BigDecimal fxValue) {
        this.fxValue = fxValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}