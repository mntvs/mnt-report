package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;

public enum JRExportType implements JRExportAction {
    XLSX(new JRExportXLSX()) , PDF(new JRExportPDF()), DOC(new JRExportDOC()), NULL(new JRExportNULL());

    private final JRExportAction jrExportAction;

    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint, String configScript) {
        return jrExportAction.export(jasperPrint, configScript);
    }

    @Override
    public String getExtension() {
        return jrExportAction.getExtension();
    }

    @Override
    public String getMimeType() {
        return jrExportAction.getMimeType();
    }


    JRExportType(JRExportAction jrExportAction) {
        this.jrExportAction = jrExportAction;
    }
}
