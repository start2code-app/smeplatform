package com.gcs.gcsplatform.service;

import javax.annotation.Nullable;

import com.haulmont.cuba.core.entity.FileDescriptor;

public interface WorkDocsUploadService {

    String NAME = "gcsplatform_WorkDocsUploadService";

    /**
     * Uploads specified file to Amazon WorkDocs.
     *
     * @param fileDescriptor File descriptor
     * @param folderId       WorkDocs folder id
     */
    void uploadFile(FileDescriptor fileDescriptor, String folderId);
}