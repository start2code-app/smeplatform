package com.gcs.gcsplatform.entity.trade;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TradeSide implements EnumClass<String> {

    BUY("B"),
    SELL("S");

    private String id;

    TradeSide(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TradeSide fromId(String id) {
        for (TradeSide at : TradeSide.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}