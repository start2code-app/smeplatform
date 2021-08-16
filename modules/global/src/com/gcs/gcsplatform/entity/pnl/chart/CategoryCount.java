package com.gcs.gcsplatform.entity.pnl.chart;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "gcsplatform_CategoryCount")
public class CategoryCount extends BaseUuidEntity {

    private static final long serialVersionUID = -8464001931684199705L;

    @MetaProperty
    private String category;

    @MetaProperty
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}