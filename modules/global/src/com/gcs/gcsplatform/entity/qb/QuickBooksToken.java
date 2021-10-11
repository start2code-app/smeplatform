package com.gcs.gcsplatform.entity.qb;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@Table(name = "GCSPLATFORM_QUICK_BOOKS_TOKEN")
@Entity(name = "gcsplatform_QuickBooksToken")
public class QuickBooksToken extends StandardEntity {

    private static final long serialVersionUID = 7537428580195513554L;

    @Column(name = "ACCESS_TOKEN", nullable = false, length = 1000)
    @NotNull
    private String accessToken;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 1000)
    @NotNull
    private String refreshToken;

    @Column(name = "REALM_ID", nullable = false)
    @NotNull
    private String realmId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRE_TS", nullable = false)
    @NotNull
    private Date expireTs;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REFRESH_TOKEN_EXPIRE_TS", nullable = false)
    @NotNull
    private Date refreshTokenExpireTs;

    @Transient
    @MetaProperty(related = "refreshTokenExpireTs")
    public Boolean getRefreshTokenExpired() {
        return refreshTokenExpireTs.compareTo(getCurrentDate()) < 0;
    }

    @Transient
    @MetaProperty(related = "expireTs")
    public Boolean getAccessTokenExpired() {
        return expireTs.compareTo(getCurrentDate()) < 0;
    }

    public Date getRefreshTokenExpireTs() {
        return refreshTokenExpireTs;
    }

    public void setRefreshTokenExpireTs(Date refreshTokenExpireTs) {
        this.refreshTokenExpireTs = refreshTokenExpireTs;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public Date getExpireTs() {
        return expireTs;
    }

    public void setExpireTs(Date expireTs) {
        this.expireTs = expireTs;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}