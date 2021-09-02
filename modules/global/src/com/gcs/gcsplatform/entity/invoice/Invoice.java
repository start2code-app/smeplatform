package com.gcs.gcsplatform.entity.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

@Table(name = "GCSPLATFORM_INVOICE")
@Entity(name = "gcsplatform_Invoice")
public class Invoice extends StandardEntity {

    private static final long serialVersionUID = -38073166780749348L;

    @Column(name = "ISSUE")
    private Integer issue = 1;

    @Column(name = "CURRENCY", length = 10)
    private String currency;

    @Column(name = "COUNTERPARTY_CODE", length = 10)
    private String counterpartyCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "AMOUNT", precision = 10, scale = 4)
    private BigDecimal amount;

    @Column(name = "FX", precision = 10, scale = 4)
    private BigDecimal fx;

    @Column(name = "FX_USD", precision = 10, scale = 4)
    private BigDecimal fxUsd;

    @Column(name = "GBP_AMOUNT", precision = 10, scale = 4)
    private BigDecimal gbpAmount;

    @Column(name = "POSTED")
    private Boolean posted;

    @Column(name = "PRINTED")
    private Boolean printed;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceLine> lines;

    public BigDecimal getGbpAmount() {
        return gbpAmount;
    }

    public void setGbpAmount(BigDecimal gbpAmount) {
        this.gbpAmount = gbpAmount;
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

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Boolean getPrinted() {
        return printed;
    }

    public void setPrinted(Boolean printed) {
        this.printed = printed;
    }

    public Boolean getPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCounterpartyCode() {
        return counterpartyCode;
    }

    public void setCounterpartyCode(String counterpartyCode) {
        this.counterpartyCode = counterpartyCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(List<InvoiceLine> lines) {
        this.lines = lines;
    }
}