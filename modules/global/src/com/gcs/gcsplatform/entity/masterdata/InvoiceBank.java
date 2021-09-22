package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_INVOICE_BANK", indexes = {
        @Index(name = "IDX_GCSPLATFORM_INVOICE_BANK_UNQ", columnList = "CURRENCY_ID, LOCATION", unique = true)
})
@Entity(name = "gcsplatform_InvoiceBank")
public class InvoiceBank extends StandardEntity {

    private static final long serialVersionUID = -1563996886407064776L;

    @Column(name = "LOCATION", nullable = false, length = 5)
    @NotNull
    private String location;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}