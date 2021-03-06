package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JRReportException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static com.mntviews.jreport.JRDefaultContext.checkIfNullThenDefault;

@JsonDeserialize(builder = JRReport.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JRReport {

    @JsonProperty("template")
    private final JRTemplate template;

    @JsonProperty("exportType")
    private final JRExportType exportType;

    @JsonProperty("paramList")
    private final List<JRParam> paramList;

    @JsonProperty("fileName")
    private final String fileName;

    @JsonProperty("isZip")
    private final Boolean isZip;

    @JsonProperty("configScript")
    private final String configScript;

    @JsonIgnore
    private final JRBeforeReportAction jrBeforeReportAction;

    public JRReport(Builder builder) {
        JRReport jrReport = JRDefaultContext.getJrReport();
        this.isZip = checkIfNullThenDefault(builder.isZip, jrReport == null ? null : jrReport.isZip);
        this.configScript = checkIfNullThenDefault(builder.configScript, jrReport == null ? null : jrReport.configScript);
        this.fileName = checkIfNullThenDefault(builder.fileName, jrReport == null ? null : jrReport.fileName);
        this.jrBeforeReportAction = checkIfNullThenDefault(builder.jrBeforeReportAction, JRDefaultContext.getJrBeforeReportAction());
        this.paramList = checkIfNullThenDefault(builder.paramList, jrReport == null ? null : List.copyOf(JRDefaultContext.getJrParamList()));

        this.template = builder.template;
        this.exportType = builder.exportType;
    }

    public JRTemplate getTemplate() {
        return template;
    }

    public JRExportType getExportType() {
        return exportType;
    }

    public String getFileName() {
        return fileName;
    }

    public static Builder custom(JRTemplate jrTemplate) {
        return new Builder(jrTemplate);
    }

    /**
     * Executes report and makes output JRFile . Also executes arbitrary action before/
     * @param jRConnection connection to the database. If null then predefined connection
     * @return generated JRFile
     */
    public JRFile execute(JRConnection jRConnection) {
        HashMap<String, Object> properties = new HashMap<>();
        if (this.paramList != null) {
            this.paramList.forEach(p -> properties.put(p.getKey(), p.getVal()));
        }
        JRGzipVirtualizer virtualizer = new JRGzipVirtualizer(4);

        try {
            Connection connection = null;
            if (null != jRConnection)
                connection = jRConnection.getConnection();

            if (jrBeforeReportAction != null)
                jrBeforeReportAction.execute(connection);

            properties.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            return JRFile.custom(exportType.export(JasperFillManager.fillReport(template.compile(), properties, connection),configScript), fileName, exportType);
        } catch (JRException | SQLException e) {
            throw new JRReportException(e);
        } finally {
            virtualizer.cleanup();
        }
    }

    @JsonPOJOBuilder
    public static class Builder {
        private final JRTemplate template;
        private JRExportType exportType;
        private String fileName;
        private List<JRParam> paramList;
        private JRBeforeReportAction jrBeforeReportAction;
        private Boolean isZip;
        private String configScript;


        @JsonCreator
        public Builder(@JsonProperty("template") JRTemplate template) {
            this.template = template;
        }

        public JRReport build() {
            return new JRReport(this);
        }

        @JsonProperty("exportType")
        public Builder exportType(String exportTypeTag) {
            try {
                this.exportType = JRExportType.valueOf(exportTypeTag);
            } catch (IllegalArgumentException e) {
                throw new JRReportException(exportTypeTag + " is not defined");
            }
            return this;
        }

        public Builder withExportType(JRExportType exportType) {
            this.exportType = exportType;
            return this;
        }

        public Builder withBeforeReportAction(JRBeforeReportAction jrBeforeReportAction) {
            this.jrBeforeReportAction = jrBeforeReportAction;
            return this;
        }


        @JsonProperty("fileName")
        public Builder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        @JsonProperty("paramList")
        public Builder withParamList(List<JRParam> paramList) {
            this.paramList = paramList;
            return this;
        }


        @JsonProperty("isZip")
        public Builder withIsZip(Boolean isZip) {
            this.isZip = isZip;
            return this;
        }

        @JsonProperty("configScript")
        public Builder withConfigScript(String configScript) {
            this.configScript = configScript;
            return this;
        }

    }

}
