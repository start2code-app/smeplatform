package com.gcs.gcsplatform.exception;

public class QuickBooksException extends RuntimeException {

    public QuickBooksException(String message) {
        super(message);
    }

    public QuickBooksException(String message, Throwable cause) {
        super(message, cause);
    }
}
