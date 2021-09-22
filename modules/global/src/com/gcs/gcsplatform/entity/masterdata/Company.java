package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_COMPANY")
@Entity(name = "gcsplatform_Company")
@NamePattern("%s|companyName")
public class Company extends StandardEntity {

    private static final long serialVersionUID = 3850791492866128675L;

    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    private String companyName;

    @Column(name = "LOCATION", nullable = false, unique = true, length = 5)
    @NotNull
    private String location;

    @Column(name = "ADDRESS1", length = 50)
    private String companyAddress1;

    @Column(name = "ADDRESS2", length = 50)
    private String companyAddress2;

    @Column(name = "TEL", length = 50)
    private String tel;

    @Column(name = "ADDITIONAL_COMPANY_NAME_LINE", length = 50)
    private String additionalCompanyNameLine;

    public String getAdditionalCompanyNameLine() {
        return additionalCompanyNameLine;
    }

    public void setAdditionalCompanyNameLine(String additionalCompanyNameLine) {
        this.additionalCompanyNameLine = additionalCompanyNameLine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompanyAddress2() {
        return companyAddress2;
    }

    public void setCompanyAddress2(String companyAddress2) {
        this.companyAddress2 = companyAddress2;
    }

    public String getCompanyAddress1() {
        return companyAddress1;
    }

    public void setCompanyAddress1(String companyAddress1) {
        this.companyAddress1 = companyAddress1;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}