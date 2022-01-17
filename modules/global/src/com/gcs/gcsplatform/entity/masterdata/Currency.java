package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gcs.gcsplatform.entity.ExtStandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@Table(name = "GCSPLATFORM_CURRENCY", indexes = {
        @Index(name = "IDX_GCSPLATFORM_CURRENCY_UNIQ_CURRENCY", columnList = "CURRENCY, DELETE_TS_NN", unique = true)
})
@Entity(name = "gcsplatform_Currency")
@NamePattern("%s|currency")
public class Currency extends ExtStandardEntity {

    private static final long serialVersionUID = -1582437732616937460L;

    @Column(name = "CURRENCY", nullable = false, length = 5)
    @NotNull
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}