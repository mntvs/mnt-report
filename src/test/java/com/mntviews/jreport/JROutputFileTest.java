package com.mntviews.jreport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests output to the file
 */
class JROutputFileTest {

    JROutputFile outputFile;

    private final static String TEST_STRING = "text";

    @BeforeEach
    public void init(@TempDir Path tempDir) {
        outputFile = JROutputFile.custom().withPath(tempDir.toString()).build();
    }

    /**
     * Saves JRFile to the disk and checks the content after that
     */
    @Test
    void saveDataToFileTest() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(TEST_STRING.getBytes());
        List<JRFile> jrFileList = new ArrayList<>();
        JRFile jrFile = JRFile.custom(byteArrayOutputStream, TEST_STRING, JRExportType.XLSX);
        jrFileList.add(jrFile);
        outputFile.execute(jrFileList);
        String inputTest = Files.readString(Paths.get(outputFile.getPath() + "/" + jrFile.getFileName()));
        assertEquals(TEST_STRING, inputTest);
    }
}
