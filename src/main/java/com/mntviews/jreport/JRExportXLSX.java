package com.mntviews.jreport;

import com.mntviews.jreport.exception.JRExportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;

public class JRExportXLSX implements JRExportAction {
    final static String EXTENSION = "xlsx";
    final static String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporterXlsx = new JRXlsxExporter();

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();

        configuration.setFontSizeFixEnabled(true);

     //   configuration.setOnePagePerSheet(false);


        configuration.setMaxRowsPerSheet(1000000);
        configuration.setRemoveEmptySpaceBetweenColumns(true);

        configuration.setRemoveEmptySpaceBetweenRows(true);


        exporterXlsx.setConfiguration(configuration);

        exporterXlsx.setExporterInput(new SimpleExporterInput(jasperPrint));

        exporterXlsx.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));


        try {
            exporterXlsx.exportReport();
        } catch (JRException e) {
            throw new JRExportException(e);
        }

        return outputStream;

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
