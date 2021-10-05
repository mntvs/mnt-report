package com.mntviews.jreport.base;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.mntviews.jreport.base.JRConnectionBaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

/**
 * The base class for sending email
 */
public class JRMailBaseTest extends JRConnectionBaseTest {
    final protected static String FROM_TEST = "megahitgroup1@gmail.com";
    final protected static String TO_TEST = "kuznetsovstv@gmail.com";
    final protected static String USERNAME_TEST = "megahitgroup1@gmail.com";
    final protected static String HOST_TEST = "127.0.0.1";
    final protected static String PORT_TEST = "2525";
    final protected static String PASSWORD_TEST = "123";

    protected static GreenMail greenMail;

    /**
     * Creates mock smtp server with default parameters
     */
    @BeforeAll
    public static void setupSMTP() {
        greenMail = new GreenMail(new ServerSetup(Integer.parseInt(PORT_TEST), HOST_TEST, "smtp"));
        greenMail.setUser(USERNAME_TEST, PASSWORD_TEST);
        greenMail.start();
    }

    /**
     * Shutdowns mock smtp server
     */
    @AfterAll
    public static void tearDownSMTP() {
        greenMail.stop();
    }

    /**
     * Cleans mailboxes after all tests
     */
    @AfterEach
    public void cleanup() throws FolderException {
        greenMail.purgeEmailFromAllMailboxes();
    }

}
