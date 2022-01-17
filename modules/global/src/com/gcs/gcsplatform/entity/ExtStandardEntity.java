package com.gcs.gcsplatform.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.cuba.core.entity.StandardEntity;

/**
 * Entity that allows to use SoftDelete on MySQL DB correctly.
 *
 * See https://doc.cuba-platform.com/manual-latest/db_mysql_features.html
 */
@MappedSuperclass
@MetaClass(name = "gcsplatform_ExtStandardEntity")
public class ExtStandardEntity extends StandardEntity {

    private static final long serialVersionUID = 1524761553935504260L;

    @NotNull
    @Column(name = "DELETE_TS_NN", nullable = false, insertable = false)
    private Date deleteTsNN;

    public Date getDeleteTsNN() {
        return deleteTsNN;
    }

}