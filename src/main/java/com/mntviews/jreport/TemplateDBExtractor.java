package com.mntviews.jreport;

import java.sql.Connection;

public interface TemplateDBExtractor {
    String findTemplateByTag(Connection connection,String tag);
}
