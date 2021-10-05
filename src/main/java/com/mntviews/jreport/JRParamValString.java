package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("string")
final public class JRParamValString extends JRParam {
    @JsonProperty("val")
    final private String val;

    @Override
    public Object getVal() {
        return val;
    }

    @JsonCreator
    private JRParamValString(@JsonProperty("key") String key,@JsonProperty("val") String val) {
        super(key);
        this.val = val;
    }

    public static JRParamValString createOf(String key, String val) {
        return new JRParamValString(key, val);
    }
}
