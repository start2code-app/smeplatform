package com.gcs.gcsplatform.core.qb;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component("gcsplatform_QuickBooksClientManagerMBean")
public class QuickBooksClientManager implements QuickBooksClientManagerMBean {

    @Inject
    private QuickBooksClientFactory quickBooksClientFactory;

    @Override
    public String refreshQuickBooksClient() {
        quickBooksClientFactory.refreshQuickBooksClient();
        return "Refreshed successfully";
    }
}
