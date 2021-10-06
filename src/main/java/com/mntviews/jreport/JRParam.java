package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JRParamValString.class),
        @JsonSubTypes.Type(value = JRParamValNumber.class)
})
abstract public class JRParam {
    @JsonProperty("key")
    final private String key;

    protected JRParam(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    abstract public Object getVal();
}
