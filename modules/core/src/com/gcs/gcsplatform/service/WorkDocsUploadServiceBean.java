package com.gcs.gcsplatform.service;

import javax.inject.Inject;

import com.gcs.gcsplatform.core.workdocs.AmazonWorkDocsUploader;
import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import org.springframework.stereotype.Service;

@Service(WorkDocsUploadService.NAME)
public class WorkDocsUploadServiceBean implements WorkDocsUploadService {

    @Inject
    private AmazonWorkDocsUploader amazonWorkDocsUploader;
    @Inject
    private FileStorageAPI fileStorageAPI;

    @Override
    public void uploadFile(FileDescriptor fileDescriptor, String folderId) {
        try {
            if (!fileStorageAPI.fileExists(fileDescriptor)) {
                throw new IllegalArgumentException("File does not exist");
            }
            byte[] content = fileStorageAPI.loadFile(fileDescriptor);
            amazonWorkDocsUploader.uploadFile(fileDescriptor.getName(), folderId, content);
        } catch (FileStorageException e) {
            throw new RuntimeException("An error occurred while loading file from storage", e);
        }
    }
}