package com.mntviews.jreport;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JRReportTest {
    JRReport reportXLSX;

    @Mock
    JRTemplateSource templateSource;


    @BeforeEach
    void Init() {
        reportXLSX = JRReport.custom(JRTemplate.custom(templateSource).build()).withExportType(JRExportType.XLSX).build();
        when(templateSource.load()).thenReturn(new ByteArrayInputStream(TestContext.getTestTemplate().getBytes(StandardCharsets.UTF_8)));
    }
    @Test
    void test() {
        JRFile jrFile = reportXLSX.execute(null);
        assertNotNull(jrFile);
    }
}
