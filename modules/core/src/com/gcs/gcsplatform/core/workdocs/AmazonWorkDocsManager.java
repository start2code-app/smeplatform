package com.gcs.gcsplatform.core.workdocs;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component("gcsplatform_AmazonWorkDocsManagerMBean")
public class AmazonWorkDocsManager implements AmazonWorkDocsManagerMBean {

    @Inject
    private AmazonWorkDocsUploader amazonWorkDocsUploader;

    @Override
    public String refreshWorkDocsClient() {
        amazonWorkDocsUploader.refreshWorkDocsClient();
        return "Refreshed successfully";
    }
}
