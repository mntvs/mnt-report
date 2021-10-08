package com.mntviews.jreport.base;

import org.flywaydb.core.Flyway;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;


/**
 * Base class for tests with database access
 */
@Testcontainers
public class JRDBBaseTest {

    private final static String DATABASE_NAME = "mnt";
    private final static String USER_NAME = "mnt";
    private final static String PASSWORD = "secret";

    @Container
    protected PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD);

    /**
     * Initialization for postgres test container. Gets sql scripts from db.migration anp process them to the database
     */
    protected void init() throws IOException, SQLException {
        Flyway.configure()
                .dataSource(postgresqlContainer.getJdbcUrl(), postgresqlContainer.getUsername(), postgresqlContainer.getPassword()).locations("classpath:db/migration")
                .load()
                .migrate();
    }

    /**
     * Converts resource file to the String
     *
     * @param fileName the source name of the file
     * @return the result string
     * @throws IOException if there is io errors
     */
    protected String getStringFromResource(String fileName) throws IOException {
        try (InputStream inputStream = JRDBBaseTest.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null)
                return null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            }

        }

    }
}
