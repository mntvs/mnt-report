package com.mntviews.jreport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JRTemplateSourceFileTest {
    private static String TEST_JRXML = "test.jrxml";

    JRTemplateSourceFile templateSourceFile;
    String testJrxml;

    @BeforeEach
    void init() throws URISyntaxException, IOException {

        templateSourceFile = JRTemplateSourceFile.custom().path(TestContext.getResourcePath()).name(TEST_JRXML).build();

    }

    @Test
    void loadTemplateSourceFile() throws IOException {
        InputStream inputStream = templateSourceFile.load();
        assertNotNull(inputStream);
        String text;
        try (Reader reader = new InputStreamReader(inputStream)) {
            text = CharStreams.toString(reader);
        }
       // assertEquals(text,testJrxml);
    }


}
