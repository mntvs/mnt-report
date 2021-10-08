package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JRTemplateException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@JsonDeserialize(builder = JRTemplateSourceFile.Builder.class)
public class JRTemplateSourceFile extends JRTemplateSource {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("path")
    private final String path;

    public static Builder custom() {
        return new Builder();
    }

    private JRTemplateSourceFile(Builder builder) {
        this.name = builder.name;
        this.path = builder.path;
    }

    @Override
    InputStream load() {

        try {
            Path pathToTemplate = Paths.get(path + '/' + name);
            return Files.newInputStream(pathToTemplate);
        } catch (Exception e) {
            throw new JRTemplateException(e);
        }
    }

    @JsonPOJOBuilder
    public static class Builder {
        @JsonProperty("name")
        private String name;
        @JsonProperty("path")
        private String path;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public JRTemplateSourceFile build() {
            return new JRTemplateSourceFile(this);
        }
    }


}
