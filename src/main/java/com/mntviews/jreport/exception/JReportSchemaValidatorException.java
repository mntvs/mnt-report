package com.mntviews.jreport.exception;

public class JReportSchemaValidatorException extends RuntimeException {
    public JReportSchemaValidatorException() {
    }

    public JReportSchemaValidatorException(String message) {
        super(message);
    }

    public JReportSchemaValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JReportSchemaValidatorException(Throwable cause) {
        super(cause);
    }
}

