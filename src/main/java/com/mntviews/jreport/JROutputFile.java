package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JROutputException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@JsonDeserialize(builder = JROutputFile.Builder.class)
public class JROutputFile extends JROutput {

    @JsonProperty("path")
    private final String path;

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    private JROutputFile(Builder builder) {
        path = builder.path;
    }

    public static Builder custom() {
        return new Builder();
    }

    @Override
    public void execute(List<JRFile> fileList) {

        for (JRFile file : fileList) {
            try (OutputStream outputStream = new FileOutputStream(path + "/" + file.getFileName())) {
                file.getData().writeTo(outputStream);
            } catch (Exception e) {
                throw new JROutputException(e);
            } finally {
                if (file.getData() != null) {
                    try {
                        file.getData().close();
                    } catch (IOException e) {
                        throw new JROutputException(e);
                    }
                }
            }
        }
    }

    @JsonPOJOBuilder
    static class Builder {
        private String path;

        @JsonProperty("path")
        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public JROutputFile build() {
            return new JROutputFile(this);
        }
    }
}
