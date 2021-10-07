package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.InputStream;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JRTemplateSourceDB.class, name = "db"),
        @JsonSubTypes.Type(value = JRTemplateSourceFile.class, name = "file")
})
public abstract class JRTemplateSource {
    abstract InputStream load();
}
