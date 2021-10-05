package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JRTemplateException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;


@JsonDeserialize(builder = JRTemplateSourceDB.Builder.class)
public class JRTemplateSourceDB extends JRTemplateSource {
    @JsonProperty("tag")
    private final String tag;

    @JsonProperty("connection")
    private JRConnection jRConnection;

    public String getTag() {
        return tag;
    }

    @JsonIgnore
    private TemplateDBExtractor templateDBExtractor;

    public JRConnection getJRConnection() {
        return jRConnection;
    }

    public TemplateDBExtractor getTemplateDBExtractor() {
        return templateDBExtractor;
    }

    private JRTemplateSourceDB(Builder builder) {
        this.tag = builder.tag;
        this.jRConnection = builder.connection;
        this.templateDBExtractor = builder.templateDBExtractor;
    }

    static public Builder custom(String tag) {
        return new Builder(tag);
    }

    static public Builder custom(JRTemplateSourceDB jrTemplateSourceDB) {
        return new Builder(jrTemplateSourceDB.getTag()).withConnection(jrTemplateSourceDB.getJRConnection()).withTemplateDBExtractor(jrTemplateSourceDB.getTemplateDBExtractor());
    }

    @Override
    InputStream load() {
        String template;
        Connection connection;
        if (jRConnection == null) {
            if (JRDefaultContext.getJrConnection() == null) {
                connection = null;
            } else
                connection = JRDefaultContext.getJrConnection().getConnection();
        } else
            connection = jRConnection.getConnection();

        if (connection == null)
            throw new JRTemplateException("Connection is null");

        if (templateDBExtractor == null)
            template = JRDefaultContext.getJrTemplateSourceDB().getTemplateDBExtractor().findTemplateByTag(connection, tag);
        else
            template = templateDBExtractor.findTemplateByTag(connection, tag);

        if (template == null) {
            throw new JRTemplateException("Template with tag=" + tag + " not found");
        }

        return
                new ByteArrayInputStream(template.getBytes((StandardCharsets.UTF_8)));
    }

    @JsonPOJOBuilder
    public static class Builder {
        private final String tag;
        private JRConnection connection;
        private TemplateDBExtractor templateDBExtractor;

        @JsonCreator
        public Builder(@JsonProperty("tag") String tag) {
            this.tag = tag;
        }

        @JsonProperty("connection")
        public Builder withConnection(JRConnection connection) {
            this.connection = connection;
            return this;
        }

        @JsonIgnore
        public Builder withTemplateDBExtractor(TemplateDBExtractor templateDBExtractor) {
            this.templateDBExtractor = templateDBExtractor;
            return this;
        }

        public JRTemplateSourceDB build() {
            return new JRTemplateSourceDB(this);
        }


    }
}
