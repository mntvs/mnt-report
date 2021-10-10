package com.mntviews.jreport;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JRTemplateTest {

    @Mock
    JRTemplateSource templateSource;

    JRTemplate template;

    @BeforeEach
    void init() {
        template = JRTemplate.custom(templateSource).build();
        when(templateSource.load()).thenReturn(new ByteArrayInputStream("test".getBytes()));
    }

    @Test
    void loadTemplate() {
        assertNotNull(template.load());
    }

}
