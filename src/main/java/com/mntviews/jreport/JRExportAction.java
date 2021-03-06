package com.mntviews.jreport;

import net.sf.jasperreports.engine.JasperPrint;
import java.io.ByteArrayOutputStream;

public interface JRExportAction {
    ByteArrayOutputStream export(JasperPrint jasperPrint, String configScript);
    String getExtension();
    String getMimeType();
}
