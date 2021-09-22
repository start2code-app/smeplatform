package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_COMPANY")
@Entity(name = "gcsplatform_Company")
@NamePattern("%s|name")
public class Company extends StandardEntity {

    private static final long serialVersionUID = 3850791492866128675L;

    @Column(name = "NAME", nullable = false, length = 50)
    @NotNull
    private String name;

    @Column(name = "LOCATION", nullable = false, unique = true, length = 5)
    @NotNull
    private String location;

    @Column(name = "ADDRESS1", length = 50)
    private String address1;

    @Column(name = "ADDRESS2", length = 50)
    private String address2;

    @Column(name = "TEL", length = 50)
    private String tel;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}