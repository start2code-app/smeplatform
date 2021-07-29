package com.gcs.gcsplatform.entity.masterdata;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum CounterpartyBrokerageType implements EnumClass<String> {

    GC("GC"),
    SPECIAL("SPECIAL");

    private String id;

    CounterpartyBrokerageType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CounterpartyBrokerageType fromId(String id) {
        for (CounterpartyBrokerageType at : CounterpartyBrokerageType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}