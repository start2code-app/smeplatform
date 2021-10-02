package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@NamePattern("%s|name")
@Table(name = "GCSPLATFORM_LOCATION")
@Entity(name = "gcsplatform_Location")
public class Location extends StandardEntity {

    private static final long serialVersionUID = -3226411038033629811L;

    @Column(name = "NAME", nullable = false, unique = true, length = 5)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}