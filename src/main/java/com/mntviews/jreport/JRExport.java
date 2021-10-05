package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = JRReport.Builder.class)
public class JRExport {

    private final JRExportType exportType;

    private JRExport(Builder builder) {
        this.exportType = JRExportType.valueOf(builder.exportTypeTag);
    }

    @JsonProperty("exportType")
    public String getExportTypeTag() {
        return this.exportType.toString();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private final String exportTypeTag;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public Builder(@JsonProperty("export") String jRExportTypeTag) {
            this.exportTypeTag =jRExportTypeTag;
        }

        public JRExport build() {
            return new JRExport(this);
        }
    }

}
