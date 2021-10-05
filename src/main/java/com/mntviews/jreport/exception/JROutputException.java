package com.mntviews.jreport.exception;

public class JROutputException extends RuntimeException {
    public JROutputException() {
    }

    public JROutputException(String message) {
        super(message);
    }

    public JROutputException(String message, Throwable cause) {
        super(message, cause);
    }

    public JROutputException(Throwable cause) {
        super(cause);
    }
}
