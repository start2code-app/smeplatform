package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.EmbeddedParameters;

@MappedSuperclass
public abstract class TradeContainer extends StandardEntity {

    private static final long serialVersionUID = 2936750977213134084L;

    @Embedded
    @EmbeddedParameters(nullAllowed = false)
    private Trade trade;

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }
}
