package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JRPackageException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.mntviews.jreport.JRDefaultContext.checkIfNullThenDefault;

@JsonDeserialize(builder = JRPackage.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JRPackage {
    @JsonProperty("outputList")
    private final List<JROutput> outputList;
    @JsonProperty("reportList")
    private final List<JRReport> reportList;

    @JsonProperty("connection")
    private final JRConnection connection;

    final static private ObjectMapper objectMapper = new ObjectMapper();
    ;

    private JRPackage(Builder builder) {
        this.outputList = builder.outputList;
        this.reportList = builder.reportList;
        this.connection = checkIfNullThenDefault(builder.connection, JRDefaultContext.getJrConnection());
    }

    public static Builder custom() {
        return new JRPackage.Builder();
    }


    public static JRPackage deserializeJRPackage(InputStream scriptStream) {
        try {
            return objectMapper.readValue(scriptStream, JRPackage.class);
        } catch (IOException e) {
            throw new JRPackageException(e);
        }
    }

    public static JRPackage deserializeJRPackage(String script) {
        return deserializeJRPackage(new ByteArrayInputStream(script.getBytes()));
    }


    public static String serializeJRPackage(JRPackage jRPackage) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jRPackage);
        } catch (JsonProcessingException e) {
            throw new JRPackageException(e);
        }
    }


    @JsonPOJOBuilder
    public static class Builder {
        private List<JROutput> outputList;
        private List<JRReport> reportList;
        private JRConnection connection;

        Builder() {
        }

        public JRPackage build() {
            return new JRPackage(this);
        }

        @JsonProperty("outputList")
        public Builder setOutput(List<JROutput> outputList) {
            this.outputList = outputList;
            return this;
        }

        @JsonProperty("reportList")
        public Builder setReportList(List<JRReport> reportList) {
            this.reportList = reportList;
            return this;
        }

        @JsonProperty("connection")
        public Builder setConnection(JRConnection connection) {
            this.connection = connection;
            return this;
        }

    }

    public List<JRFile> execute() {
        List<JRFile> jrFileList = reportList.stream().map(r -> r.execute(connection)).collect(Collectors.toList());
        if (outputList != null)
            outputList.stream().forEach(r -> r.execute(jrFileList));
        return jrFileList;
    }
}
