package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseStringIdEntity;

@NamePattern("%s|dealer")
@Table(name = "dealer") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_Dealer")
public class Dealer extends BaseStringIdEntity {

    private static final long serialVersionUID = -2373023222222239327L;

    @Id
    @Column(name = "`Dealer`", nullable = false, length = 10)
    private String dealer;

    @Column(name = "active", nullable = false, length = 1)
    private String active;

    @Column(name = "`Admin`", nullable = false, length = 1)
    private String admin;

    @Column(name = "application", nullable = false, length = 45)
    private String application;

    @Column(name = "`DealerName`", nullable = false, length = 30)
    private String dealerName;

    @Column(name = "`Password`", nullable = false, length = 45)
    private String password;

    @Column(name = "`Sheet`", nullable = false, length = 45)
    private String sheet;

    @Column(name = "`SheetNew`", nullable = false, length = 200)
    private String sheetNew;

    public String getSheetNew() {
        return sheetNew;
    }

    public void setSheetNew(String sheetNew) {
        this.sheetNew = sheetNew;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public void setId(String id) {
        this.dealer = id;
    }

    @Override
    public String getId() {
        return dealer;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }
}