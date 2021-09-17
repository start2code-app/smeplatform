package com.gcs.gcsplatform.entity.masterdata;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_COUNTERPARTY_BROKERAGE", indexes = {
        @Index(name = "IDX_GCSPLATFORM_COUNTERPARTY_BROKERAGE_UNQ",
                columnList = "COUNTERPARTY_ID, CATEGORY_ID, BROKERAGE_TYPE", unique = true)
})
@Entity(name = "gcsplatform_CounterpartyBrokerage")
public class CounterpartyBrokerage extends StandardEntity {

    private static final long serialVersionUID = 5757870870908910960L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTERPARTY_ID")
    @NotNull
    private Counterparty counterparty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CATEGORY_ID")
    @NotNull
    private Category category;

    @Column(name = "BROKERAGE_VALUE", nullable = false, precision = 10, scale = 4)
    @NotNull
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal brokerageValue;

    @NotNull
    @Column(name = "BROKERAGE_TYPE", nullable = false)
    private String brokerageType;

    public BrokerageType getBrokerageType() {
        return brokerageType == null ? null : BrokerageType.fromId(brokerageType);
    }

    public void setBrokerageType(BrokerageType brokerageType) {
        this.brokerageType = brokerageType == null ? null : brokerageType.getId();
    }

    public BigDecimal getBrokerageValue() {
        return brokerageValue;
    }

    public void setBrokerageValue(BigDecimal value) {
        this.brokerageValue = value;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}