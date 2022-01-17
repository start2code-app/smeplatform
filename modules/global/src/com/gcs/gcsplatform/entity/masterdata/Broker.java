package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gcs.gcsplatform.entity.ExtStandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.security.entity.User;

@NamePattern("%s|name")
@Table(name = "GCSPLATFORM_BROKER", indexes = {
        @Index(name = "IDX_GCSPLATFORM_BROKER_UNIQ_NAME", columnList = "NAME, DELETE_TS_NN", unique = true),
        @Index(name = "IDX_GCSPLATFORM_BROKER_UNIQ_USER", columnList = "USER_ID, DELETE_TS_NN", unique = true)
})
@Entity(name = "gcsplatform_Broker")
public class Broker extends ExtStandardEntity {

    private static final long serialVersionUID = -2373023222222239327L;

    @Column(name = "NAME", nullable = false, length = 10)
    private String name;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}