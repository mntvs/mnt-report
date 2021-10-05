package com.mntviews.jreport;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestContext {

    final private static String TEST_JRXML = "test.jrxml";

    private static String testTemplate;

    private static String resourcePath;

    public static String getTestTemplate() {
        return testTemplate;
    }

    public static String getResourcePath() {
        return resourcePath;
    }

    static {
        try {
            URI uri = ClassLoader.getSystemResource("template").toURI();
            resourcePath = Paths.get(uri).toString();
            testTemplate = Files.readString(Paths.get(resourcePath, TEST_JRXML));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
