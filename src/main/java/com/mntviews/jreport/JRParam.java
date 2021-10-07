package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JRParamValString.class),
        @JsonSubTypes.Type(value = JRParamValNumber.class)
})
public abstract class JRParam {
    @JsonProperty("key")
    private final String key;

    protected JRParam(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public abstract Object getVal();
}
