package com.gcs.gcsplatform.entity.invoice;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.gcs.gcsplatform.entity.HasNumdays;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.TradeSide;
import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_INVOICE_LINE")
@Entity(name = "gcsplatform_InvoiceLine")
public class InvoiceLine extends StandardEntity implements HasNumdays {

    private static final long serialVersionUID = 4221618502379911685L;

    @Column(name = "CONTRACT_NUMBER", length = 100)
    private String contractNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRADE_DATE")
    private Date tradeDate;

    @Column(name = "BOND_DESCRIPTION", length = 50)
    private String bondDescription;

    @Column(name = "BUYER", length = 30)
    private String buyer;

    @Column(name = "SELLER", length = 30)
    private String seller;

    @Column(name = "COUNTERPARTY", length = 30)
    private String counterparty;

    @Column(name = "COUNTERPARTY_CODE", length = 10)
    private String counterpartyCode;

    @Column(name = "BROKER", length = 10)
    private String broker;

    @Column(name = "NOTES", length = 200)
    private String notes;

    @Column(name = "ISIN", length = 20)
    private String isin;

    @Column(name = "LOCATION", length = 5)
    private String location;

    @Column(name = "CURRENCY", length = 10)
    private String currency;

    @Column(name = "BASE_CURRENCY", length = 10)
    private String baseCurrency;

    @Column(name = "CROSS_RATE", length = 50)
    private String crossRate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALUE_DATE")
    private Date valueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MATURITY_DATE")
    private Date maturityDate;

    @Column(name = "NUMDAYS")
    private Long numdays;

    @Column(name = "NOMINAL", precision = 10, scale = 4)
    private BigDecimal nominal;

    @Column(name = "HAIR_CUT", precision = 10, scale = 4)
    private BigDecimal hairCut;

    @Column(name = "REPO_RATE", precision = 10, scale = 4)
    private BigDecimal repoRate;

    @Column(name = "XRATE", precision = 10, scale = 4)
    private BigDecimal xrate = BigDecimal.ONE;

    @Column(name = "BROKERAGE", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal brokerage;

    @Column(name = "START_PRICE", precision = 10, scale = 4)
    private BigDecimal startPrice;

    @Column(name = "PNL", precision = 10, scale = 4)
    private BigDecimal pnl;

    @Column(name = "FX", precision = 10, scale = 4)
    private BigDecimal fx;

    @Column(name = "FX_USD", precision = 10, scale = 4)
    private BigDecimal fxUsd;

    @Column(name = "GBP_EQUIVALENT", precision = 10, scale = 4)
    private BigDecimal gbpEquivalent;

    @Column(name = "CASH")
    private Boolean cash;

    @NotNull
    @Column(name = "TRADE_SIDE", nullable = false)
    private String tradeSide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRADE_ID")
    private ClosedTrade trade;

    public void setBuyerOrSeller(String counterparty) {
        if (getTradeSide() == TradeSide.BUY) {
            setBuyer(counterparty);
        } else {
            setSeller(counterparty);
        }
    }

    public TradeSide getTradeSide() {
        return tradeSide == null ? null : TradeSide.fromId(tradeSide);
    }

    public void setTradeSide(TradeSide tradeSide) {
        this.tradeSide = tradeSide == null ? null : tradeSide.getId();
    }

    public BigDecimal getFxUsd() {
        return fxUsd;
    }

    public void setFxUsd(BigDecimal fxUsd) {
        this.fxUsd = fxUsd;
    }

    public BigDecimal getFx() {
        return fx;
    }

    public void setFx(BigDecimal fx) {
        this.fx = fx;
    }

    public String getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(String crossRate) {
        this.crossRate = crossRate;
    }

    public ClosedTrade getTrade() {
        return trade;
    }

    public void setTrade(ClosedTrade trade) {
        this.trade = trade;
    }

    public BigDecimal getRepoRate() {
        return repoRate;
    }

    public void setRepoRate(BigDecimal repoRate) {
        this.repoRate = repoRate;
    }

    public BigDecimal getHairCut() {
        return hairCut;
    }

    public void setHairCut(BigDecimal hairCut) {
        this.hairCut = hairCut;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getCounterpartyCode() {
        return counterpartyCode;
    }

    public void setCounterpartyCode(String counterpartyCode) {
        this.counterpartyCode = counterpartyCode;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Long getNumdays() {
        return numdays;
    }

    @Override
    public void setNumdays(Long numdays) {
        this.numdays = numdays;
    }

    public Boolean getCash() {
        return cash;
    }

    public void setCash(Boolean cash) {
        this.cash = cash;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getXrate() {
        return xrate;
    }

    public void setXrate(BigDecimal xrate) {
        this.xrate = xrate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public BigDecimal getGbpEquivalent() {
        return gbpEquivalent;
    }

    public void setGbpEquivalent(BigDecimal gbpEquivalent) {
        this.gbpEquivalent = gbpEquivalent;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    @Override
    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    @Override
    public Date getValueDate() {
        return valueDate;
    }

    public BigDecimal getPnl() {
        return pnl;
    }

    public void setPnl(BigDecimal pnl) {
        this.pnl = pnl;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(BigDecimal brokerage) {
        this.brokerage = brokerage;
    }

    public String getBondDescription() {
        return bondDescription;
    }

    public void setBondDescription(String bondDescription) {
        this.bondDescription = bondDescription;
    }
}