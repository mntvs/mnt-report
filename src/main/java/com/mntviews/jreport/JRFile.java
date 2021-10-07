package com.mntviews.jreport;

import java.io.ByteArrayOutputStream;

public class JRFile {
    private final ByteArrayOutputStream data;
    private final String name;
    private final JRExportType exportType;

    private JRFile(ByteArrayOutputStream data, String name, JRExportType exportType) {
        this.data = data;
        this.name = name;
        this.exportType = exportType;
    }

    public static JRFile custom(ByteArrayOutputStream data, String name, JRExportType exportType) {
        return new JRFile(data, name, exportType);
    }

    public ByteArrayOutputStream getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public JRExportType getExportType() {
        return exportType;
    }

    public String getMimeType() {
        return exportType.getMimeType();
    }


    public String getFileName() {
        return name + "." + exportType.getExtension();

    }
}
