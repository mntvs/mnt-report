package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class JRExportZIP implements JRExportAction {
    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint) {
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
