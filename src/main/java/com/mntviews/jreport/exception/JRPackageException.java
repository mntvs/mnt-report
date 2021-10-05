package com.mntviews.jreport.exception;

public class JRPackageException extends RuntimeException {
    public JRPackageException() {
    }

    public JRPackageException(String message) {
        super(message);
    }

    public JRPackageException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRPackageException(Throwable cause) {
        super(cause);
    }
}
