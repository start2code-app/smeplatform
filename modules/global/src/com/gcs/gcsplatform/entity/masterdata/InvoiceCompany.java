package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_INVOICE_COMPANY")
@Entity(name = "gcsplatform_InvoiceCompany")
public class InvoiceCompany extends StandardEntity {

    private static final long serialVersionUID = -7835360934029415951L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOCATION_ID", unique = true)
    private Location location;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID", unique = true)
    private Company company;

    @Column(name = "WORK_DOCS_FOLDER_ID")
    private String workDocsFolderId;

    @Column(name = "QB_REALM_ID")
    private String qbRealmId;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getQbRealmId() {
        return qbRealmId;
    }

    public void setQbRealmId(String qbRealmId) {
        this.qbRealmId = qbRealmId;
    }

    public String getWorkDocsFolderId() {
        return workDocsFolderId;
    }

    public void setWorkDocsFolderId(String workDocsFolderId) {
        this.workDocsFolderId = workDocsFolderId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}