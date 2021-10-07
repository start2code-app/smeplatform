package com.gcs.gcsplatform.service.qb;

import javax.annotation.Nullable;

import com.gcs.gcsplatform.entity.qb.QuickBooksToken;
import com.haulmont.cuba.core.global.View;

public interface QuickBooksTokenService {

    String NAME = "gcsplatform_QuickBooksTokenService";

    /**
     * Searches for a QuickBooks token with corresponding realm. Returns null if no such exist.
     *
     * @param realmId QuickBooks realm id
     * @param view    View
     * @return QuickBooks token or null
     */
    @Nullable
    QuickBooksToken findLatestToken(String realmId, View view);

    /**
     * Gets latest CSRF token. Throws an exception if no such exist.
     *
     * @return CSRF token
     */
    String getLatestCsrf();
}