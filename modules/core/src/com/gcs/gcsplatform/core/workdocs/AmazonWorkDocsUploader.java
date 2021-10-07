package com.gcs.gcsplatform.core.workdocs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.AmazonWorkDocsConfig;
import com.gcs.gcsplatform.exception.WorkDocsException;
import com.haulmont.cuba.core.sys.events.AppContextStartedEvent;
import org.apache.commons.io.IOUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.workdocs.WorkDocsClient;
import software.amazon.awssdk.services.workdocs.model.DocumentVersionStatus;
import software.amazon.awssdk.services.workdocs.model.InitiateDocumentVersionUploadRequest;
import software.amazon.awssdk.services.workdocs.model.InitiateDocumentVersionUploadResponse;
import software.amazon.awssdk.services.workdocs.model.UpdateDocumentVersionRequest;
import software.amazon.awssdk.services.workdocs.model.UploadMetadata;

import static java.net.HttpURLConnection.HTTP_OK;

@Component(AmazonWorkDocsUploader.NAME)
public class AmazonWorkDocsUploader {

    public static final String NAME = "gcsplatform_AmazonWorkDocsUploader";

    @Inject
    private AmazonWorkDocsConfig amazonWorkDocsConfig;

    private WorkDocsClient workDocsClient;

    @EventListener
    private void init(AppContextStartedEvent event) {
        refreshWorkDocsClient();
    }

    /**
     * Refreshes WorkDocs client with new settings.
     */
    public void refreshWorkDocsClient() {
        workDocsClient = WorkDocsClient.builder()
                .credentialsProvider(getAwsCredentialsProvider())
                .region(Region.of(amazonWorkDocsConfig.getRegion()))
                .build();
    }

    private AwsCredentialsProvider getAwsCredentialsProvider() {
        String accessKey = amazonWorkDocsConfig.getAccessKey();
        String secretAccessKey = amazonWorkDocsConfig.getSecretAccessKey();
        if (accessKey != null && secretAccessKey != null) {
            AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretAccessKey);
            return StaticCredentialsProvider.create(awsCredentials);
        } else {
            return DefaultCredentialsProvider.builder().build();
        }
    }

    /**
     * Uploads a file to Amazon WorkDocs.
     *
     * @param fileName    Target file name
     * @param folderId    WorkDocs folder id
     * @param content     File content
     * @param contentType File content type
     */
    public void uploadFile(String fileName, String folderId, byte[] content, String contentType) {
        InitiateDocumentVersionUploadResponse response = initiateUpload(fileName, folderId, contentType);
        UploadMetadata metadata = response.uploadMetadata();
        String uploadUrl = metadata.uploadUrl();

        try {
            int httpCode = uploadContent(uploadUrl, content, contentType);
            if (httpCode != HTTP_OK) {
                throw new WorkDocsException("Connection to WorkDocs failed with HTTP code " + httpCode);
            }
        } catch (IOException e) {
            throw new WorkDocsException("An error occurred while uploading file to WorkDocs", e);
        }

        String docId = response.metadata().id();
        String versionId = response.metadata().latestVersionMetadata().id();
        updateDocVersion(docId, versionId);
    }

    private InitiateDocumentVersionUploadResponse initiateUpload(String fileName, String folderId, String contentType) {
        InitiateDocumentVersionUploadRequest request = InitiateDocumentVersionUploadRequest.builder()
                .parentFolderId(folderId)
                .name(fileName)
                .contentType(contentType)
                .build();
        return workDocsClient.initiateDocumentVersionUpload(request);
    }

    private int uploadContent(String uploadUrl, byte[] content, String contentType) throws IOException {
        URL url = new URL(uploadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("x-amz-server-side-encryption", "AES256");

        try (OutputStream outputStream = connection.getOutputStream()) {
            try (InputStream inputStream = new ByteArrayInputStream(content)) {
                IOUtils.copy(inputStream, outputStream);
            }
        }
        return connection.getResponseCode();
    }

    private void updateDocVersion(String docId, String versionId) {
        UpdateDocumentVersionRequest request = UpdateDocumentVersionRequest.builder()
                .documentId(docId)
                .versionId(versionId)
                .versionStatus(DocumentVersionStatus.ACTIVE)
                .build();
        workDocsClient.updateDocumentVersion(request);
    }
}
