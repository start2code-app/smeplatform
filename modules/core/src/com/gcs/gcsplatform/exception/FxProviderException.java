package com.gcs.gcsplatform.exception;

public class FxProviderException extends RuntimeException {

    public FxProviderException(String message) {
        super(message);
    }

    public FxProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
