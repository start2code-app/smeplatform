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
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

@PublishEntityChangedEvents
@NamePattern("%s|counterparty")
@Table(name = "GCSPLATFORM_COUNTERPARTY", indexes = {
        @Index(name = "IDX_GCSPLATFORM_COUNTERPARTY_UNIQ_COUNTERPARTY", columnList = "COUNTERPARTY, DELETE_TS_NN",
                unique = true)
})
@Entity(name = "gcsplatform_Counterparty")
public class Counterparty extends ExtStandardEntity {

    private static final long serialVersionUID = -1999181841396498391L;

    @Column(name = "COUNTERPARTY", nullable = false, length = 30)
    private String counterparty;

    @Column(name = "ADDRESS1", length = 50)
    private String address1;

    @Column(name = "ADDRESS2", length = 50)
    private String address2;

    @Column(name = "ADDRESS3", length = 50)
    private String address3;

    @Column(name = "ADDRESS4", length = 50)
    private String address4;

    @Column(name = "BILLING_NAME", length = 50)
    private String billingName;

    @Column(name = "BILLING_COMPANY_NAME", length = 50)
    private String billingCompanyName;

    @Column(name = "BILLING_COUNTRY", length = 5)
    private String billingCountry;

    @Column(name = "BILLING_INFO", length = 50)
    private String billingInfo;

    @Column(name = "CODE", length = 10)
    private String code;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "CONTACT_NAME", length = 50)
    private String contactName;

    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "EMAIL1", length = 50)
    private String email1;

    @Column(name = "EMAIL2", length = 50)
    private String email2;

    @Column(name = "CASH")
    private Boolean cash;

    @Column(name = "COMMISSION_OVERRIDE")
    private Boolean commissionOverride;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(String billingInfo) {
        this.billingInfo = billingInfo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getCommissionOverride() {
        return commissionOverride;
    }

    public void setCommissionOverride(Boolean commissionOverride) {
        this.commissionOverride = commissionOverride;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getCash() {
        return cash;
    }

    public void setCash(Boolean cash) {
        this.cash = cash;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingCompanyName() {
        return billingCompanyName;
    }

    public void setBillingCompanyName(String billingCompanyName) {
        this.billingCompanyName = billingCompanyName;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }
}