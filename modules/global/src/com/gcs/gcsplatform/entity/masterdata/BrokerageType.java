package com.gcs.gcsplatform.entity.masterdata;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum BrokerageType implements EnumClass<String> {

    GC("GEN"),
    SPECIAL("SPE"),
    SUB_THIRTY_DAYS("S30D"),
    MORE_THAN_THIRTY_DAYS("M30D"),
    SUB_TEN_MILLION("S10M");

    private String id;

    BrokerageType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static BrokerageType fromId(String id) {
        for (BrokerageType at : BrokerageType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}