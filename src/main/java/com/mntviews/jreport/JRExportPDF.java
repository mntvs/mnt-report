package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;

public class JRExportPDF implements JRExportAction {
    static final String EXTENSION = "pdf";
    private static final String MIME_TYPE = "application/pdf";

    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint, String configScript) {
        return null;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }
}
