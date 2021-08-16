package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_CURRENCY")
@Entity(name = "gcsplatform_Currency")
@NamePattern("%s|currency")
public class Currency extends StandardEntity {

    private static final long serialVersionUID = -1582437732616937460L;

    @Column(name = "CURRENCY", nullable = false, unique = true, length = 5)
    @NotNull
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}