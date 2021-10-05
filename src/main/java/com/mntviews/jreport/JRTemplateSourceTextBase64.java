package com.mntviews.jreport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JRTemplateSourceTextBase64 extends JRTemplateSource {
    @Override
    InputStream load() {
        return new ByteArrayInputStream(new byte[0]);
    }
}
