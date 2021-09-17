package com.gcs.gcsplatform.entity.pnl.chart;

import com.gcs.gcsplatform.entity.masterdata.BrokerageType;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "gcsplatform_BrokerageTypeCount")
public class BrokerageTypeCount extends BaseUuidEntity {

    private static final long serialVersionUID = -5074447856834502194L;

    @MetaProperty
    private String brokerageType;

    @MetaProperty
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BrokerageType getBrokerageType() {
        return brokerageType == null ? null : BrokerageType.fromId(brokerageType);
    }

    public void setBrokerageType(BrokerageType brokerageType) {
        this.brokerageType = brokerageType == null ? null : brokerageType.getId();
    }
}