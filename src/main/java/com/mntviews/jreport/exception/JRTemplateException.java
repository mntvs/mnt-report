package com.mntviews.jreport.exception;

public class JRTemplateException extends RuntimeException {
    public JRTemplateException() {
    }

    public JRTemplateException(String message) {
        super(message);
    }

    public JRTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRTemplateException(Throwable cause) {
        super(cause);
    }
}
