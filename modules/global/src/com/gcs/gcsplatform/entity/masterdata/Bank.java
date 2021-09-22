package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_BANK")
@Entity(name = "gcsplatform_Bank")
public class Bank extends StandardEntity {

    private static final long serialVersionUID = 6445836540888375347L;

    @Column(name = "BANK_NAME", nullable = false, length = 50)
    @NotNull
    private String bankName;

    @Column(name = "ACCOUNT_NAME", nullable = false, length = 50)
    @NotNull
    private String accountName;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 50)
    @NotNull
    private String accountNumber;

    @Column(name = "SORT_CODE", nullable = false, length = 50)
    @NotNull
    private String sortCode;

    @Column(name = "SWIFT_BIC", nullable = false, length = 50)
    @NotNull
    private String swiftBic;

    @Column(name = "ADDITIONAL_BANK_LINE_CAPTION", length = 50)
    private String additionalBankLineCaption;

    @Column(name = "ADDITIONAL_BANK_LINE", length = 50)
    private String additionalBankLine;

    @Column(name = "ADDITIONAL_LINE1", length = 100)
    private String additionalLine1;

    @Column(name = "ADDITIONAL_LINE2", length = 100)
    private String additionalLine2;

    @Column(name = "ADDITIONAL_LINE3", length = 100)
    private String additionalLine3;

    @Column(name = "ADDITIONAL_LINE4", length = 100)
    private String additionalLine4;

    @Column(name = "ADDITIONAL_LINE5", length = 100)
    private String additionalLine5;

    public String getAdditionalLine5() {
        return additionalLine5;
    }

    public void setAdditionalLine5(String additionalLine5) {
        this.additionalLine5 = additionalLine5;
    }

    public String getAdditionalLine4() {
        return additionalLine4;
    }

    public void setAdditionalLine4(String additionalLine4) {
        this.additionalLine4 = additionalLine4;
    }

    public String getAdditionalLine3() {
        return additionalLine3;
    }

    public void setAdditionalLine3(String additionalLine3) {
        this.additionalLine3 = additionalLine3;
    }

    public String getAdditionalLine2() {
        return additionalLine2;
    }

    public void setAdditionalLine2(String additionalLine2) {
        this.additionalLine2 = additionalLine2;
    }

    public String getAdditionalLine1() {
        return additionalLine1;
    }

    public void setAdditionalLine1(String additionalLine1) {
        this.additionalLine1 = additionalLine1;
    }

    public String getAdditionalBankLine() {
        return additionalBankLine;
    }

    public void setAdditionalBankLine(String additionalBankLine) {
        this.additionalBankLine = additionalBankLine;
    }

    public String getAdditionalBankLineCaption() {
        return additionalBankLineCaption;
    }

    public void setAdditionalBankLineCaption(String additionalBankLineCaption) {
        this.additionalBankLineCaption = additionalBankLineCaption;
    }

    public String getSwiftBic() {
        return swiftBic;
    }

    public void setSwiftBic(String swiftBic) {
        this.swiftBic = swiftBic;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}