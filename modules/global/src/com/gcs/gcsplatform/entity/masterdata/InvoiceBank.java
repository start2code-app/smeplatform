package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gcs.gcsplatform.entity.ExtStandardEntity;

@Table(name = "GCSPLATFORM_INVOICE_BANK", indexes = {
        @Index(name = "IDX_GCSPLATFORM_INVOICE_BANK_UNQ", columnList = "CURRENCY_ID, LOCATION_ID, DELETE_TS_NN",
                unique = true)
})
@Entity(name = "gcsplatform_InvoiceBank")
public class InvoiceBank extends ExtStandardEntity {

    private static final long serialVersionUID = -1563996886407064776L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

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

}