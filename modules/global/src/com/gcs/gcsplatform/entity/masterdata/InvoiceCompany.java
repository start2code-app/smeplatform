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

@Table(name = "GCSPLATFORM_INVOICE_COMPANY", indexes = {
        @Index(name = "IDX_GCSPLATFORM_INVOICE_COMPANY_UNIQ_COMPANY_ID", columnList = "COMPANY_ID, DELETE_TS_NN",
                unique = true),
        @Index(name = "IDX_GCSPLATFORM_INVOICE_COMPANY_UNIQ_LOCATION_ID", columnList = "LOCATION_ID, DELETE_TS_NN",
                unique = true)
})
@Entity(name = "gcsplatform_InvoiceCompany")
public class InvoiceCompany extends ExtStandardEntity {

    private static final long serialVersionUID = -7835360934029415951L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
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