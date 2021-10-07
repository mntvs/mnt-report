package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;

public class JRExportDOC  implements JRExportAction {
    static final String EXTENSION = "doc";

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
        return "";
    }
}
