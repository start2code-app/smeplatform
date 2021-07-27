package com.gcs.gcsplatform.entity.trade;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import com.haulmont.cuba.core.entity.BaseIntIdentityIdEntity;
import com.haulmont.cuba.core.entity.annotation.EmbeddedParameters;

@MappedSuperclass
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "TRADE_ID"))
})
public abstract class TradeContainer extends BaseIntIdentityIdEntity {

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
