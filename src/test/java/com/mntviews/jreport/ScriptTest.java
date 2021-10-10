package com.mntviews.jreport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mntviews.jreport.base.JRMailBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScriptTest extends JRMailBaseTest {

    final static private String PATH = "script/";
    final static private String TEMPLATE_FROM_DB = "jreport-script-templateFromDb.json";

    JRPackage jrPackage;

    @BeforeEach
    public void init() {
        jrPackage = JRPackage.deserializeJRPackage(this.getClass().getClassLoader().getResourceAsStream(PATH + TEMPLATE_FROM_DB));
    }

    @Test
    void validateScript() {
        String result = JRPackage.serializeJRPackage(jrPackage);
        JRSchemaValidator.validate(result);

        assertNotNull(jrPackage);
    }

}
