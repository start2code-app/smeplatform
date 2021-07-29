package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_BROKERAGE_CATEGORY")
@Entity(name = "gcsplatform_BrokerageCategory")
@NamePattern("%s|category")
public class BrokerageCategory extends StandardEntity {

    private static final long serialVersionUID = 1965203073345136048L;

    @Column(name = "CATEGORY", nullable = false, length = 50)
    @NotNull
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}