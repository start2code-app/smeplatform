package com.gcs.gcsplatform.entity.pnl.chart;

import java.math.BigDecimal;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "gcsplatform_TotalPnl")
public class TotalPnl extends BaseUuidEntity {

    private static final long serialVersionUID = 6260323848694915706L;

    @MetaProperty
    private BigDecimal total;

    @MetaProperty
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}