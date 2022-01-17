package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gcs.gcsplatform.entity.ExtStandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@Table(name = "GCSPLATFORM_CATEGORY", indexes = {
        @Index(name = "IDX_GCSPLATFORM_CATEGORY_UNIQ_CATEGORY", columnList = "CATEGORY, DELETE_TS_NN", unique = true)
})
@Entity(name = "gcsplatform_Category")
@NamePattern("%s|category")
public class Category extends ExtStandardEntity {

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