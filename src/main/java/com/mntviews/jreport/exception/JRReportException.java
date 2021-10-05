package com.mntviews.jreport.exception;

public class JRReportException extends RuntimeException {

    public JRReportException() {
    }

    public JRReportException(String message) {
        super(message);
    }

    public JRReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRReportException(Throwable cause) {
        super(cause);
    }
}
