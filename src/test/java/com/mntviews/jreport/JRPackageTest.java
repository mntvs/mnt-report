package com.mntviews.jreport;

import com.mntviews.jreport.base.JRMailBaseTest;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JRPackageTest extends JRMailBaseTest {

    final private static String TEMPLATE_FILENAME_TEST = "test.jrxml";

    private JRPackage jrPackage;
    private JRPackage jrPackageSourceDB;

    @BeforeEach
    public void init() throws IOException, SQLException {

        super.init();
        JROutputMail jrOutputMail = JROutputMail.custom()
                .setBody("test")
                .setSubject("test")
                .setTo(TO_TEST)
                .setFrom(FROM_TEST)
                .setHost(HOST_TEST)
                .setPort(PORT_TEST)
                .setUserName(USERNAME_TEST)
                .setPassword(PASSWORD_TEST).build();


        JRReport jrReport = JRReport.custom(JRTemplate
                .custom(JRTemplateSourceFile
                        .custom()
                        .setPath(TestContext.getResourcePath())
                        .setName(TEMPLATE_FILENAME_TEST)
                        .build())
                .build())
                .exportType(JRExportType.XLSX)
                .fileName("test")
                .paramList(List.of(JRParamValString.createOf("testParamString", "qwerty")
                        , JRParamValNumber.createOf("testParamNumber", 34567785L)))
                .build();


        jrPackage = JRPackage.custom()
                .setConnection(jrConnection)
                .setOutput(List.of(jrOutputMail))
                .setReportList(List.of(jrReport)).build();


        JRReport jrReportSourceDB = JRReport.custom(JRTemplate
                .custom(JRTemplateSourceDB
                        .custom(TEMPLATE_FILENAME_TEST)
                        .withConnection(jrConnection)
                        .build())
                .build())
                .exportType(JRExportType.XLSX)
                .fileName("test")
                .paramList(List.of(JRParamValString.createOf("testParamString", "qwerty")
                        , JRParamValNumber.createOf("testParamNumber", 34567785L)))
                .build();


        String testTemplate = getStringFromResource("template/" + TEMPLATE_FILENAME_TEST);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(jrConnection.getConnection());
        jdbcTemplate.execute("insert into report_template (tag,data) values (?,?)", TEMPLATE_FILENAME_TEST, testTemplate);


        jrPackageSourceDB = JRPackage.custom()
                .setConnection(jrConnection)
                .setOutput(List.of(jrOutputMail))
                .setReportList(List.of(jrReportSourceDB)).build();
    }

    @Test
    void executeSourceFile() throws MessagingException {
        jrPackage.execute();


        if (greenMail.waitForIncomingEmail(1)) {
            MimeMessage testMessage = greenMail.getReceivedMessages()[0];
            assertEquals(testMessage.getSubject(), "test");
            assertEquals(testMessage.getRecipients(Message.RecipientType.TO)[0].toString(), TO_TEST);
            assertEquals(testMessage.getFrom()[0].toString(), FROM_TEST);
        } else {
            Assertions.fail("email not sent");
        }

    }


    @Test
    void executeSourceDB() throws MessagingException {
        jrPackageSourceDB.execute();

        if (greenMail.waitForIncomingEmail(1)) {
            MimeMessage testMessage = greenMail.getReceivedMessages()[0];
            assertEquals(testMessage.getSubject(), "test");
            assertEquals(testMessage.getRecipients(Message.RecipientType.TO)[0].toString(), TO_TEST);
            assertEquals(testMessage.getFrom()[0].toString(), FROM_TEST);
        } else {
            Assertions.fail("email not sent");
        }

    }


}
