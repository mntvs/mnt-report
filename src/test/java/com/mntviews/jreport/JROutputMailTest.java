package com.mntviews.jreport;

import com.mntviews.jreport.base.JRMailBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests sending JRFile by email
 */
public class  JROutputMailTest extends JRMailBaseTest {

    final protected static String TEST_STRING = "text";
    final private static String BODY_TEST = "test";
    final private static String SUBJECT_TEST = "test";


    JROutputMail jrOutputMail;

    @BeforeEach
    public void init() {
        jrOutputMail = JROutputMail.custom()
                .body(BODY_TEST)
                .subject(SUBJECT_TEST)
                .to(TO_TEST)
                .from(FROM_TEST)
                .host(HOST_TEST)
                .port(PORT_TEST)
                .userName(USERNAME_TEST)
                .password(PASSWORD_TEST).build();
    }


    @Test
    public void sendMailTest() throws IOException, MessagingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(TEST_STRING.getBytes());
        List<JRFile> jrFileList = new ArrayList<>();
        JRFile jrFile = JRFile.custom(byteArrayOutputStream, TEST_STRING, JRExportType.XLSX);
        jrFileList.add(jrFile);
        jrOutputMail.execute(jrFileList);

        if (greenMail.waitForIncomingEmail(1)) {
            MimeMessage testMessage = greenMail.getReceivedMessages()[0];
            Assertions.assertEquals(testMessage.getSubject(), SUBJECT_TEST);
            Assertions.assertEquals(testMessage.getRecipients(Message.RecipientType.TO)[0].toString(), TO_TEST);
            Assertions.assertEquals(testMessage.getFrom()[0].toString(), FROM_TEST);
            //MimeMultipart mimeMultipart = (MimeMultipart)testMessage.getContent();
            //assertEquals(mimeMultipart.getBodyPart(0).getFileName(), TEST_STRING);
        } else {
            Assertions.fail("email not sent");
        }
    }

}
