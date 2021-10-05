package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class JRExportDOC  implements JRExportAction {
    final static String EXTENSION = "doc";


    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint) {
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
