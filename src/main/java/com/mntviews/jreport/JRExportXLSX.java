package com.mntviews.jreport;

import com.mntviews.jreport.exception.JRExportException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;

public class JRExportXLSX implements JRExportAction {
    static final String CONFIGURATION_VARIABLE = "confXlsx";
    static final String EXTENSION = "xlsx";
    static final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    public ByteArrayOutputStream export(JasperPrint jasperPrint, String configScript) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporterXlsx = new JRXlsxExporter();

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setFontSizeFixEnabled(true);
        configuration.setMaxRowsPerSheet(1000000);
        configuration.setRemoveEmptySpaceBetweenColumns(true);

        configuration.setRemoveEmptySpaceBetweenRows(true);

        if (configScript != null) {
            Binding binding = new Binding();
            binding.setVariable(CONFIGURATION_VARIABLE, configuration);
            GroovyShell groovyShell = new GroovyShell(binding);
            groovyShell.evaluate(configScript);
        }

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
