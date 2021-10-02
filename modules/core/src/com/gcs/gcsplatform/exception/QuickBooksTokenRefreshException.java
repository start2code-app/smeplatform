package com.gcs.gcsplatform.exception;

public class QuickBooksTokenRefreshException extends QuickBooksException {

    public QuickBooksTokenRefreshException(String message) {
        super(message);
    }

    public QuickBooksTokenRefreshException(String message, Throwable cause) {
        super(message, cause);
    }
}
