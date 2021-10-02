package com.gcs.gcsplatform.service.qb;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.qb.QuickBooksToken;
import com.gcs.gcsplatform.exception.QuickBooksException;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

@Service(QuickBooksTokenService.NAME)
public class QuickBooksTokenServiceBean implements QuickBooksTokenService {

    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public QuickBooksToken findLatestToken(String realmId, View view) {
        return dataManager.load(QuickBooksToken.class)
                .query("select e from gcsplatform_QuickBooksToken e "
                        + "where e.realmId = :realmId "
                        + "order by e.updateTs desc")
                .cacheable(true)
                .parameter("realmId", realmId)
                .view(view)
                .optional()
                .orElse(null);
    }

    @Override
    public String getLatestCsrf() {
        return dataManager.loadValue("select e.csrf from gcsplatform_QuickBooksCsrf e "
                + "order by e.createTs desc", String.class)
                .optional()
                .orElseThrow(() -> new QuickBooksException("CSRF was not found"));
    }
}