package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.gcs.gcsplatform.entity.ExtStandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "GCSPLATFORM_LOCATION", indexes = {
        @Index(name = "IDX_GCSPLATFORM_LOCATION_UNIQ_NAME", columnList = "NAME, DELETE_TS_NN", unique = true)
})
@Entity(name = "gcsplatform_Location")
public class Location extends ExtStandardEntity {

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