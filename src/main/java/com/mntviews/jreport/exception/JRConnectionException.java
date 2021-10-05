package com.mntviews.jreport.exception;

public class JRConnectionException extends RuntimeException {
    public JRConnectionException() {
    }

    public JRConnectionException(String message) {
        super(message);
    }

    public JRConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRConnectionException(Throwable cause) {
        super(cause);
    }
}
