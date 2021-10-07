package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("number")
public final class JRParamValNumber extends JRParam {
    @JsonProperty("val")
    Long val;

    @Override
    public Object getVal() {
        return val;
    }

    @JsonCreator
    private JRParamValNumber(@JsonProperty("key") String key,@JsonProperty("val") Long val) {
        super(key);
        this.val = val;
    }

    public static JRParamValNumber createOf(String key, Long val) {
        return new JRParamValNumber(key, val);
    }
}
