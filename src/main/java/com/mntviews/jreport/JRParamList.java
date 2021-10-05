package com.mntviews.jreport;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(builder = JRParamList.Builder.class)
public class JRParamList {
    @JsonProperty("reportList")
    private final List<JRParam> jrParamList;

    JRParamList(JRParamList.Builder builder) {
        jrParamList = builder.jrParamList;
    }

    public static JRParamList.Builder custom(List<JRParam> jrParamList) {
        return new JRParamList.Builder(jrParamList);
    }

    public static JRParamList.Builder custom() {
        return new JRParamList.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private final List<JRParam> jrParamList;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public Builder(@JsonProperty("paramList") List<JRParam> jrParamList) {
            this.jrParamList = jrParamList;
        }

        public Builder() {
            this.jrParamList = new ArrayList<>();
        }

        public JRParamList.Builder add(JRParam jrParam) {
            this.jrParamList.add(jrParam);
            return this;
        }

        public JRParamList build() {
            return new JRParamList(this);
        }
    }

}
