package com.gcs.gcsplatform.entity.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.security.entity.User;

@NamePattern("%s|name")
@Table(name = "GCSPLATFORM_BROKER")
@Entity(name = "gcsplatform_Broker")
public class Broker extends StandardEntity {

    private static final long serialVersionUID = -2373023222222239327L;

    @Column(name = "NAME", nullable = false, unique = true, length = 10)
    private String name;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", unique = true)
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