package com.gcs.gcsplatform.service;

import java.util.List;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.masterdata.Trader;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.haulmont.cuba.core.global.View;

public interface TraderService {

    String NAME = "gcsplatform_TraderService";

    /**
     * Gets traders associated with specified counterparty. If counterparty is null, returns all the traders.
     *
     * @param counterparty Counterparty
     * @param view         View
     * @return List of traders
     */
    List<Trader> getTraders(@Nullable Counterparty counterparty, View view);
}