package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JROutputFile.class, name = "file"),
        @JsonSubTypes.Type(value = JROutputMail.class, name = "mail")
})
public abstract class JROutput {
    public abstract void execute(List<JRFile> fileList);
}
