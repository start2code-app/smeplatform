package com.gcs.gcsplatform.entity.invoice;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NumberFormat;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_INVOICE")
@Entity(name = "gcsplatform_Invoice")
public class Invoice extends StandardEntity {

    private static final long serialVersionUID = -38073166780749348L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "XLSX_FILE_ID")
    private FileDescriptor xlsxFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDF_FILE_ID")
    private FileDescriptor pdfFile;

    @Column(name = "ISSUE")
    private Integer issue = 1;

    @Column(name = "CURRENCY", length = 10)
    private String currency;

    @Column(name = "COUNTERPARTY_CODE", length = 10)
    private String counterpartyCode;

    @Column(name = "LOCATION", length = 5)
    private String location;

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "AMOUNT", precision = 10, scale = 4)
    private BigDecimal amount;

    @Column(name = "FX", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal fx;

    @Column(name = "FX_USD", precision = 10, scale = 4)
    @NumberFormat(pattern = "#,##0.0000")
    private BigDecimal fxUsd;

    @Column(name = "GBP_AMOUNT", precision = 10, scale = 4)
    private BigDecimal gbpAmount;

    @Column(name = "USD_AMOUNT", precision = 10, scale = 4)
    private BigDecimal usdAmount;

    @Column(name = "USD_CROSS_RATE", precision = 10, scale = 4)
    private BigDecimal usdCrossRate;

    @Column(name = "SHOW_TOTAL_USD")
    private Boolean showTotalUsd;

    @Column(name = "POSTED_TO_WORK_DOCS")
    private Boolean postedToWorkDocs;

    @Column(name = "POSTED_TO_QB")
    private Boolean postedToQB;

    @MetaProperty(related = {"pdfFile", "xlsxFile"})
    public Boolean getPrinted() {
        return pdfFile != null && xlsxFile != null;
    }

    @MetaProperty(related = {"postedToWorkDocs", "postedToQB"})
    public Boolean getPosted() {
        return Boolean.TRUE.equals(postedToWorkDocs) || Boolean.TRUE.equals(postedToQB);
    }

    @MetaProperty(related = {"issue", "counterpartyCode", "currency", "startDate"})
    public String getInvoiceNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMyy");
        return String.format("%s%s%s-V%s", counterpartyCode, dateFormat.format(startDate), currency, issue);
    }

    @MetaProperty(related = {"counterpartyCode", "currency", "startDate"})
    public String getInvoiceNumberWithoutVersion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMyy");
        return String.format("%s%s%s", counterpartyCode, dateFormat.format(startDate), currency);
    }

    public Boolean getShowTotalUsd() {
        return showTotalUsd;
    }

    public void setShowTotalUsd(Boolean showTotalUsd) {
        this.showTotalUsd = showTotalUsd;
    }

    public BigDecimal getUsdCrossRate() {
        return usdCrossRate;
    }

    public void setUsdCrossRate(BigDecimal usdCrossRate) {
        this.usdCrossRate = usdCrossRate;
    }

    public FileDescriptor getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(FileDescriptor pdfFile) {
        this.pdfFile = pdfFile;
    }

    public FileDescriptor getXlsxFile() {
        return xlsxFile;
    }

    public void setXlsxFile(FileDescriptor xlsxFile) {
        this.xlsxFile = xlsxFile;
    }

    public BigDecimal getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(BigDecimal usdAmount) {
        this.usdAmount = usdAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public Boolean getPostedToQB() {
        return postedToQB;
    }

    public void setPostedToQB(Boolean postedToQB) {
        this.postedToQB = postedToQB;
    }

    public Boolean getPostedToWorkDocs() {
        return postedToWorkDocs;
    }

    public void setPostedToWorkDocs(Boolean postedToWorkDocs) {
        this.postedToWorkDocs = postedToWorkDocs;
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

}