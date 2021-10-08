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

    String script;

    @BeforeEach
    public void init() throws IOException, SQLException {

        super.init();
        JROutputMail jrOutputMail = JROutputMail.custom()
                .withBody("test")
                .withSubject("test")
                .withTo(TO_TEST)
                .withFrom(FROM_TEST)
                .withHost(HOST_TEST)
                .withPort(PORT_TEST)
                .withUserName(USERNAME_TEST)
                .withPassword(PASSWORD_TEST).build();


        JRReport jrReport = JRReport.custom(JRTemplate
                        .custom(JRTemplateSourceFile
                                .custom()
                                .withPath(TestContext.getResourcePath())
                                .withName(TEMPLATE_FILENAME_TEST)
                                .build())
                        .build())
                .withExportType(JRExportType.XLSX)
                .withFileName("test")
                .withParamList(List.of(JRParamValString.createOf("testParamString", "qwerty")
                        , JRParamValNumber.createOf("testParamNumber", 34567785L)))
                .build();


        jrPackage = JRPackage.custom()
                .withConnection(jrConnection)
                .withOutputList(List.of(jrOutputMail))
                .withReportList(List.of(jrReport)).build();


        JRReport jrReportSourceDB = JRReport.custom(JRTemplate
                        .custom(JRTemplateSourceDB
                                .custom(TEMPLATE_FILENAME_TEST)
                                .withConnection(jrConnection)
                                .build())
                        .build())
                .withExportType(JRExportType.XLSX)
                .withFileName("test")
                .withParamList(List.of(JRParamValString.createOf("testParamString", "qwerty")
                        , JRParamValNumber.createOf("testParamNumber", 34567785L)))
                .build();

        script = JRPackage.serializeJRPackage(jrPackage);


        String testTemplate = getStringFromResource("template/" + TEMPLATE_FILENAME_TEST);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(jrConnection.getConnection());
        jdbcTemplate.execute("insert into report_template (tag,data) values (?,?)", TEMPLATE_FILENAME_TEST, testTemplate);


        jrPackageSourceDB = JRPackage.custom()
                .withConnection(jrConnection)
                .withOutputList(List.of(jrOutputMail))
                .withReportList(List.of(jrReportSourceDB)).build();
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

    @Test
    void executeFromScript() throws MessagingException {
        JRPackage jrPackage = JRPackage.deserializeJRPackage(script);
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

}
