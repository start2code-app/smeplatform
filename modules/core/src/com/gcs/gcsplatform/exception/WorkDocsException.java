package com.gcs.gcsplatform.exception;

public class WorkDocsException extends RuntimeException {

    public WorkDocsException(String message) {
        super(message);
    }

    public WorkDocsException(String message, Throwable cause) {
        super(message, cause);
    }
}
