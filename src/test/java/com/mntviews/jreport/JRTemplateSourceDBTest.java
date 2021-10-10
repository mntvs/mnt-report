package com.mntviews.jreport;

import com.mntviews.jreport.base.JRConnectionBaseTest;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JRTemplateSourceDBTest extends JRConnectionBaseTest {
    final private static String REPORT_TAG_TEST = "TEST";
    final private static String TEMPLATE_FILENAME_TEST = "template/test.jrxml";

    JRTemplateSourceDB templateSourceDB;

    @BeforeEach
    protected void init() throws IOException, SQLException {
        super.init();
        String testTemplate = getStringFromResource(TEMPLATE_FILENAME_TEST);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(jrConnection.getConnection());
        jdbcTemplate.execute("insert into report_template (tag,data) values (?,?)",REPORT_TAG_TEST, testTemplate);
        templateSourceDB = JRTemplateSourceDB.custom(REPORT_TAG_TEST).withConnection(jrConnection).build();
    }

    @Test
    void loadTemplateFromDB() {
        InputStream inputStream = templateSourceDB.load();
        assertNotNull(inputStream);
    }
}
