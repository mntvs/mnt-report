package com.mntviews.jreport.exception;

public class JRExportException extends RuntimeException {
    public JRExportException() {
    }

    public JRExportException(String message) {
        super(message);
    }

    public JRExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRExportException(Throwable cause) {
        super(cause);
    }
}
