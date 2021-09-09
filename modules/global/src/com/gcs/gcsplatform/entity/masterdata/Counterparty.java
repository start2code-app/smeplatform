package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@NamePattern("%s|counterparty")
@Table(name = "GCSPLATFORM_COUNTERPARTY")
@Entity(name = "gcsplatform_Counterparty")
public class Counterparty extends StandardEntity {

    private static final long serialVersionUID = -1999181841396498391L;

    @Column(name = "COUNTERPARTY", nullable = false, unique = true, length = 30)
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

    @Column(name = "BILLING_INFO1", length = 50)
    private String billingInfo1;

    @Column(name = "BILLING_INFO2", length = 50)
    private String billingInfo2;

    @Column(name = "BILLING_INFO3", length = 50)
    private String billingInfo3;

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

    @Column(name = "ACTIVE")
    private Boolean active = true;

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

    public String getBillingInfo3() {
        return billingInfo3;
    }

    public void setBillingInfo3(String billingInfo3) {
        this.billingInfo3 = billingInfo3;
    }

    public String getBillingInfo2() {
        return billingInfo2;
    }

    public void setBillingInfo2(String billingInfo2) {
        this.billingInfo2 = billingInfo2;
    }

    public String getBillingInfo1() {
        return billingInfo1;
    }

    public void setBillingInfo1(String billingInfo1) {
        this.billingInfo1 = billingInfo1;
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