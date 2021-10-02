package com.gcs.gcsplatform.web.components.qb;

import javax.inject.Inject;

import com.gcs.gcsplatform.service.qb.QuickBooksConnectorService;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.web.AppUI;
import org.springframework.stereotype.Component;

@Component(QuickBooksConnectorBean.NAME)
public class QuickBooksConnectorBean {

    public static final String NAME = "gcsplatform_QuickBooksConnectorBean";

    @Inject
    private QuickBooksConnectorService quickBooksConnectorService;

    /**
     * Opens QuickBooks authorization page.
     */
    public void openOAuth2Page() {
        String connectUri = quickBooksConnectorService.getOAuth2ConnectUri();
        AppUI.getCurrent().getWebBrowserTools().showWebPage(connectUri, ParamsMap.of("target", "_self"));
    }
}