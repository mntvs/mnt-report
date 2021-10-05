package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JRReportException;
import com.mntviews.jreport.exception.JRTemplateException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@JsonDeserialize(builder = JRTemplate.Builder.class)
public class JRTemplate {
    @JsonProperty("source")
    private final JRTemplateSource templateSource;

    private JRTemplate(Builder builder) {
        if (null == builder.templateSource)
            throw new JRTemplateException("templateSource is null");
        this.templateSource = builder.templateSource;

    }

    public static Builder custom(JRTemplateSource jrTemplateSource) {
        return new JRTemplate.Builder(jrTemplateSource);
    }

    public InputStream load() {
        InputStream loadTemplateSource = templateSource.load();
        if (null == loadTemplateSource)
            throw new JRTemplateException("loadTemplateSource is null");

        return loadTemplateSource;
    }

    public JasperReport compile() {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(load());
            return jasperReport;
        } catch (JRException e) {
            throw new JRReportException(e);
        }
    }


    @JsonPOJOBuilder
    public static final class Builder {
        final private JRTemplateSource templateSource;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public Builder(@JsonProperty("source") JRTemplateSource templateSource) {
            this.templateSource = templateSource;
        }

        public JRTemplate build() {
            return new JRTemplate(this);
        }

    }
}
