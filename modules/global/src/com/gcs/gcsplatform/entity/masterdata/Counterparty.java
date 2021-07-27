package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntIdentityIdEntity;

@NamePattern("%s|name")
@Table(name = "counterparty") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_Counterparty")
public class Counterparty extends BaseIntIdentityIdEntity {

    private static final long serialVersionUID = -1999181841396498391L;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "active", length = 2)
    private String active;

    @Column(name = "`Address1`", length = 50)
    private String address1;

    @Column(name = "`Address2`", length = 50)
    private String address2;

    @Column(name = "`Address3`", length = 50)
    private String address3;

    @Column(name = "`Address4`", length = 50)
    private String address4;

    @Column(name = "billing_name", length = 50)
    private String billingName;

    @Column(name = "billing_companyname", length = 50)
    private String billingCompanyname;

    @Column(name = "billing_country", length = 5)
    private String billingCountry;

    @Column(name = "billing_info1", length = 50)
    private String billingInfo1;

    @Column(name = "billing_info2", length = 50)
    private String billingInfo2;

    @Column(name = "billing_info3", length = 50)
    private String billingInfo3;

    @Column(name = "contact_name", length = 50)
    private String contactName;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "email1", length = 50)
    private String email1;

    @Column(name = "email2", length = 50)
    private String email2;

    @Column(name = "gc_abs", length = 10)
    private String gcAbs;

    @Column(name = "gc_corp", length = 10)
    private String gcCorp;

    @Column(name = "gc_egb", length = 10)
    private String gcEgb;

    @Column(name = "gc_egbpigs", length = 10)
    private String gcEgbpigs;

    @Column(name = "gc_em", length = 10)
    private String gcEm;

    @Column(name = "gc_gilt", length = 10)
    private String gcGilt;

    @Column(name = "gc_ssa", length = 10)
    private String gcSsa;

    @Column(name = "gc_ust", length = 10)
    private String gcUst;

    @Column(name = "spec_abs", length = 10)
    private String specAbs;

    @Column(name = "spec_corp", length = 10)
    private String specCorp;

    @Column(name = "spec_egb", length = 10)
    private String specEgb;

    @Column(name = "spec_egbpigs", length = 10)
    private String specEgbpigs;

    @Column(name = "spec_em", length = 10)
    private String specEm;

    @Column(name = "spec_gilt", length = 10)
    private String specGilt;

    @Column(name = "spec_ssa", length = 10)
    private String specSsa;

    @Column(name = "spec_ust", length = 10)
    private String specUst;

    public String getSpecUst() {
        return specUst;
    }

    public void setSpecUst(String specUst) {
        this.specUst = specUst;
    }

    public String getSpecSsa() {
        return specSsa;
    }

    public void setSpecSsa(String specSsa) {
        this.specSsa = specSsa;
    }

    public String getSpecGilt() {
        return specGilt;
    }

    public void setSpecGilt(String specGilt) {
        this.specGilt = specGilt;
    }

    public String getSpecEm() {
        return specEm;
    }

    public void setSpecEm(String specEm) {
        this.specEm = specEm;
    }

    public String getSpecEgbpigs() {
        return specEgbpigs;
    }

    public void setSpecEgbpigs(String specEgbpigs) {
        this.specEgbpigs = specEgbpigs;
    }

    public String getSpecEgb() {
        return specEgb;
    }

    public void setSpecEgb(String specEgb) {
        this.specEgb = specEgb;
    }

    public String getSpecCorp() {
        return specCorp;
    }

    public void setSpecCorp(String specCorp) {
        this.specCorp = specCorp;
    }

    public String getSpecAbs() {
        return specAbs;
    }

    public void setSpecAbs(String specAbs) {
        this.specAbs = specAbs;
    }

    public String getGcUst() {
        return gcUst;
    }

    public void setGcUst(String gcUst) {
        this.gcUst = gcUst;
    }

    public String getGcSsa() {
        return gcSsa;
    }

    public void setGcSsa(String gcSsa) {
        this.gcSsa = gcSsa;
    }

    public String getGcGilt() {
        return gcGilt;
    }

    public void setGcGilt(String gcGilt) {
        this.gcGilt = gcGilt;
    }

    public String getGcEm() {
        return gcEm;
    }

    public void setGcEm(String gcEm) {
        this.gcEm = gcEm;
    }

    public String getGcEgbpigs() {
        return gcEgbpigs;
    }

    public void setGcEgbpigs(String gcEgbpigs) {
        this.gcEgbpigs = gcEgbpigs;
    }

    public String getGcEgb() {
        return gcEgb;
    }

    public void setGcEgb(String gcEgb) {
        this.gcEgb = gcEgb;
    }

    public String getGcCorp() {
        return gcCorp;
    }

    public void setGcCorp(String gcCorp) {
        this.gcCorp = gcCorp;
    }

    public String getGcAbs() {
        return gcAbs;
    }

    public void setGcAbs(String gcAbs) {
        this.gcAbs = gcAbs;
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

    public String getBillingCompanyname() {
        return billingCompanyname;
    }

    public void setBillingCompanyname(String billingCompanyname) {
        this.billingCompanyname = billingCompanyname;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}