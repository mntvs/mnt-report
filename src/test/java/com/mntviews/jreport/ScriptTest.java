package com.mntviews.jreport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScriptTest {

    final static private String PATH = "script/";
    final static private String TEMPLATE_FROM_DB = "jreport-script-templateFromDb.json";

    @Test
    void loadTemplate1AndValidate() throws IOException {

        JRPackage jrPackage = JRPackage.deserializeJRPackage(this.getClass().getClassLoader().getResourceAsStream(PATH + TEMPLATE_FROM_DB));
        String result = JRPackage.serializeJRPackage(jrPackage);
        JRSchemaValidator.validate(result);

        assertNotNull(jrPackage);
    }

}
