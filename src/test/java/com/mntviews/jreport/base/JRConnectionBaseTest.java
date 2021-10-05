package com.mntviews.jreport.base;

import com.mntviews.jreport.JRConnection;
import com.mntviews.jreport.JRDefaultContext;
import com.mntviews.jreport.exception.JRTemplateException;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JRConnectionBaseTest extends JRDBBaseTest {
    protected JRConnection jrConnection;

    /**
     * Base initialization for tests with connection to a database
     */
    protected void init() throws IOException, SQLException {
        super.init();
        jrConnection = JRConnection.custom()
                .setUrl(postgresqlContainer.getJdbcUrl())
                .setUserName(postgresqlContainer.getUsername())
                .setPassword(postgresqlContainer.getPassword())
                .setConnectionCreator((url, userName, password) -> {
                    Properties info = new Properties();
                    info.put("user", userName);
                    info.put("password", password);
                    return DriverManager.getConnection(url, info);
                })
                .build();

        JRDefaultContext.setDefaultTemplateDBExtractor((c, tag) -> {
            JdbcTemplate jt = new JdbcTemplate(c);
            try {
                return jt.queryForString("select data from report_template where tag=?", tag);
            } catch (SQLException e) {
                throw new JRTemplateException(e);
            }
        });


    }


}
