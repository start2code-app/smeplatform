package com.gcs.gcsplatform.entity.trade;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerageType;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.EmbeddableEntity;
import org.apache.commons.lang3.StringUtils;

@MetaClass(name = "gcsplatform_Trade")
@Embeddable
public class Trade extends EmbeddableEntity {

    private static final long serialVersionUID = -5968057577009538611L;

    @Column(name = "BOND_DESCRIPTION", length = 50)
    private String bondDescription;

    @Column(name = "BROOVERIDE")
    private Boolean brooveride;

    @Column(name = "BUYBROKER", length = 10)
    private String buybroker;

    @Column(name = "BUYBROKERAGE", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal buybrokerage;

    @Column(name = "BUYER", length = 30)
    private String buyer;

    @Column(name = "BUYER_AGENT", length = 30)
    private String buyerAgent;

    @Column(name = "CALLOPTION", length = 20)
    private String calloption;

    @Column(name = "CATEGORY", length = 10)
    private String category;

    @Column(name = "CPAIR1", length = 10)
    private String cpair1;

    @Column(name = "CPAIR2", length = 10)
    private String cpair2;

    @Column(name = "CPAIR3", length = 10)
    private String cpair3;

    @Column(name = "CURRENCY", length = 5)
    private String currency;

    @Column(name = "GBP_EQUIVALENT", precision = 10, scale = 4)
    private BigDecimal gbpEquivalent;

    @Column(name = "GC")
    private Boolean gc;

    @Column(name = "HAIR_CUT", precision = 10, scale = 4)
    private BigDecimal hairCut;

    @Column(name = "ISIN", length = 20)
    private String isin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MATURITY_DATE")
    private Date maturityDate;

    @Column(name = "NOMINAL", precision = 10, scale = 2)
    private BigDecimal nominal;

    @Column(name = "NOTES", length = 100)
    private String notes;

    @Column(name = "NUMDAYS")
    private Integer numdays;

    @Column(name = "ORIGTRADEREF", length = 20)
    private String origtraderef;

    @Column(name = "REPO_RATE", precision = 10, scale = 4)
    private BigDecimal repoRate;

    @Column(name = "SELLBROKER", length = 10)
    private String sellbroker;

    @Column(name = "SELLBROKERAGE", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal sellbrokerage;

    @Column(name = "SELLER", length = 30)
    private String seller;

    @Column(name = "SELLER_AGENT", length = 30)
    private String sellerAgent;

    @Column(name = "SPECIAL")
    private Boolean special;

    @Column(name = "START_PRICE", precision = 10, scale = 4)
    private BigDecimal startPrice;

    @Column(name = "STATUS", length = 10)
    private String status;

    @Column(name = "SUBS")
    private Boolean subs;

    @Column(name = "TRADE_CURRENCY", length = 5)
    private String tradeCurrency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRADE_DATE")
    private Date tradeDate;

    @Column(name = "TRADEREF", length = 20)
    private String traderef;

    @Column(name = "UTI", length = 50)
    private String uti;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALUE_DATE")
    private Date valueDate;

    @Column(name = "XRATE1", precision = 10, scale = 4)
    private BigDecimal xrate1;

    @Column(name = "XRATE2", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal xrate2;

    @Column(name = "XRATE3", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal xrate3;

    @Column(name = "SUB_THIRTY")
    private Boolean subThirty;

    @Column(name = "MORE_THAN_THIRTY")
    private Boolean moreThanThirty;

    @Column(name = "GM_SLA")
    private Boolean gmSla;

    @Column(name = "BUYER_LOCATION", length = 5)
    private String buyerLocation;

    @Column(name = "SELLER_LOCATION", length = 5)
    private String sellerLocation;

    @Column(name = "BUY_SPLIT")
    private Boolean buySplit;

    @Column(name = "BUY_SPLIT_BROKER", length = 10)
    private String buySplitBroker;

    @Column(name = "SELL_SPLIT")
    private Boolean sellSplit;

    @Column(name = "SELL_SPLIT_BROKER", length = 10)
    private String sellSplitBroker;

    @Nullable
    @Transient
    @MetaProperty(related = {"buybroker", "sellbroker"})
    public String getBrokerInitials() {
        if (StringUtils.isNotBlank(buybroker) && StringUtils.isNotBlank(sellbroker)) {
            return String.format("%s/%s", buybroker, sellbroker);
        }
        return null;
    }

    @Nullable
    @Transient
    @MetaProperty(related = {"gc", "special", "subThirty", "moreThanThirty"})
    public CounterpartyBrokerageType getBrokerageType() {
        if (Boolean.TRUE.equals(gc)) {
            return CounterpartyBrokerageType.GC;
        } else if (Boolean.TRUE.equals(special)) {
            return CounterpartyBrokerageType.SPECIAL;
        } else if (Boolean.TRUE.equals(subThirty)) {
            return CounterpartyBrokerageType.SUB_THIRTY;
        } else if (Boolean.TRUE.equals(moreThanThirty)) {
            return CounterpartyBrokerageType.MORE_THAN_THIRTY;
        }
        return null;
    }

    public String getSellSplitBroker() {
        return sellSplitBroker;
    }

    public void setSellSplitBroker(String sellSplitBroker) {
        this.sellSplitBroker = sellSplitBroker;
    }

    public String getBuySplitBroker() {
        return buySplitBroker;
    }

    public void setBuySplitBroker(String buySplitBroker) {
        this.buySplitBroker = buySplitBroker;
    }

    public Boolean getSellSplit() {
        return sellSplit;
    }

    public void setSellSplit(Boolean sellSplit) {
        this.sellSplit = sellSplit;
    }

    public Boolean getBuySplit() {
        return buySplit;
    }

    public void setBuySplit(Boolean buySplit) {
        this.buySplit = buySplit;
    }

    public String getSellerLocation() {
        return sellerLocation;
    }

    public void setSellerLocation(String sellerLocation) {
        this.sellerLocation = sellerLocation;
    }

    public String getBuyerLocation() {
        return buyerLocation;
    }

    public void setBuyerLocation(String buyerLocation) {
        this.buyerLocation = buyerLocation;
    }

    public Boolean getGmSla() {
        return gmSla;
    }

    public void setGmSla(Boolean gmSla) {
        this.gmSla = gmSla;
    }

    public Boolean getMoreThanThirty() {
        return moreThanThirty;
    }

    public void setMoreThanThirty(Boolean moreThanThirty) {
        this.moreThanThirty = moreThanThirty;
    }

    public Boolean getSubThirty() {
        return subThirty;
    }

    public void setSubThirty(Boolean subThirty) {
        this.subThirty = subThirty;
    }

    public BigDecimal getXrate3() {
        return xrate3;
    }

    public void setXrate3(BigDecimal xrate3) {
        this.xrate3 = xrate3;
    }

    public BigDecimal getXrate2() {
        return xrate2;
    }

    public void setXrate2(BigDecimal xrate2) {
        this.xrate2 = xrate2;
    }

    public BigDecimal getXrate1() {
        return xrate1;
    }

    public void setXrate1(BigDecimal xrate1) {
        this.xrate1 = xrate1;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getUti() {
        return uti;
    }

    public void setUti(String uti) {
        this.uti = uti;
    }

    public String getTraderef() {
        return traderef;
    }

    public void setTraderef(String traderef) {
        this.traderef = traderef;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public Boolean getSubs() {
        return subs;
    }

    public void setSubs(Boolean subs) {
        this.subs = subs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public String getSellerAgent() {
        return sellerAgent;
    }

    public void setSellerAgent(String sellerAgent) {
        this.sellerAgent = sellerAgent;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public BigDecimal getSellbrokerage() {
        return sellbrokerage;
    }

    public void setSellbrokerage(BigDecimal sellbrokerage) {
        this.sellbrokerage = sellbrokerage;
    }

    public String getSellbroker() {
        return sellbroker;
    }

    public void setSellbroker(String sellbroker) {
        this.sellbroker = sellbroker;
    }

    public BigDecimal getRepoRate() {
        return repoRate;
    }

    public void setRepoRate(BigDecimal repoRate) {
        this.repoRate = repoRate;
    }

    public String getOrigtraderef() {
        return origtraderef;
    }

    public void setOrigtraderef(String origtraderef) {
        this.origtraderef = origtraderef;
    }

    public Integer getNumdays() {
        return numdays;
    }

    public void setNumdays(Integer numdays) {
        this.numdays = numdays;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getHairCut() {
        return hairCut;
    }

    public void setHairCut(BigDecimal hairCut) {
        this.hairCut = hairCut;
    }

    public Boolean getGc() {
        return gc;
    }

    public void setGc(Boolean gc) {
        this.gc = gc;
    }

    public BigDecimal getGbpEquivalent() {
        return gbpEquivalent;
    }

    public void setGbpEquivalent(BigDecimal gbpEquivalent) {
        this.gbpEquivalent = gbpEquivalent;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCpair3() {
        return cpair3;
    }

    public void setCpair3(String cpair3) {
        this.cpair3 = cpair3;
    }

    public String getCpair2() {
        return cpair2;
    }

    public void setCpair2(String cpair2) {
        this.cpair2 = cpair2;
    }

    public String getCpair1() {
        return cpair1;
    }

    public void setCpair1(String cpair1) {
        this.cpair1 = cpair1;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCalloption() {
        return calloption;
    }

    public void setCalloption(String calloption) {
        this.calloption = calloption;
    }

    public String getBuyerAgent() {
        return buyerAgent;
    }

    public void setBuyerAgent(String buyerAgent) {
        this.buyerAgent = buyerAgent;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getBuybrokerage() {
        return buybrokerage;
    }

    public void setBuybrokerage(BigDecimal buybrokerage) {
        this.buybrokerage = buybrokerage;
    }

    public String getBuybroker() {
        return buybroker;
    }

    public void setBuybroker(String buybroker) {
        this.buybroker = buybroker;
    }

    public Boolean getBrooveride() {
        return brooveride;
    }

    public void setBrooveride(Boolean brooveride) {
        this.brooveride = brooveride;
    }

    public String getBondDescription() {
        return bondDescription;
    }

    public void setBondDescription(String bondDescription) {
        this.bondDescription = bondDescription;
    }
}