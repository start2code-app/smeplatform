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

import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_INVOICE_LINE")
@Entity(name = "gcsplatform_InvoiceLine")
public class InvoiceLine extends StandardEntity {

    private static final long serialVersionUID = 4221618502379911685L;

    @Column(name = "CONTRACT_NUMBER", length = 20)
    private String contractNumber;

    @Column(name = "TRADEREF", length = 20)
    private String traderef;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRADE_DATE")
    private Date tradeDate;

    @Column(name = "BOND_DESCRIPTION", length = 50)
    private String bondDescription;

    @Column(name = "BUYER", length = 30)
    private String buyer;

    @Column(name = "SELLER", length = 30)
    private String seller;

    @Column(name = "ISIN", length = 20)
    private String isin;

    @Column(name = "INVOICE_CODE", length = 5)
    private String invoiceCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALUE_DATE")
    private Date valueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MATURITY_DATE")
    private Date maturityDate;

    @Column(name = "NUMDAYS")
    private Long numdays;

    @Column(name = "BASE_CURRENCY", length = 10)
    private String baseCurrency;

    @Column(name = "NOMINAL", precision = 10, scale = 4)
    private BigDecimal nominal;

    @Column(name = "XRATE", precision = 10, scale = 4)
    private BigDecimal xrate;

    @Column(name = "BROKERAGE", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal brokerage;

    @Column(name = "START_PRICE", precision = 10, scale = 4)
    private BigDecimal startPrice;

    @Column(name = "CASH")
    private Boolean cash;

    @Column(name = "PNL", precision = 10, scale = 4)
    private BigDecimal pnl;

    @Column(name = "GBP_EQUIVALENT", precision = 10, scale = 4)
    private BigDecimal gbpEquivalent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    public String getTraderef() {
        return traderef;
    }

    public void setTraderef(String traderef) {
        this.traderef = traderef;
    }

    public Long getNumdays() {
        return numdays;
    }

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

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

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