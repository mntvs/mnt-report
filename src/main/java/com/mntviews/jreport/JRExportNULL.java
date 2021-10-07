package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;

public class JRExportNULL implements JRExportAction {
    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint, String configScript) {
        return null;
    }

    @Override
    public String getExtension() {
        return null;
    }

    @Override
    public String getMimeType() {
        return null;
    }
}
