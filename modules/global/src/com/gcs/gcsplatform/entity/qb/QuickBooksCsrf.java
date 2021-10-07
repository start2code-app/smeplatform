package com.gcs.gcsplatform.entity.qb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "GCSPLATFORM_QUICK_BOOKS_CSRF")
@Entity(name = "gcsplatform_QuickBooksCsrf")
public class QuickBooksCsrf extends StandardEntity {

    private static final long serialVersionUID = 5392471675950263239L;

    @Column(name = "CSRF", nullable = false)
    @NotNull
    private String csrf;

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }
}