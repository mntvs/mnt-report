package com.mntviews.jreport;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Represents default context loaded on the initialization. Each object can be configured by corresponding json file
 * for each part of the config
 */
public class JRDefaultContext {

    final static public String PATH = "jr-default/";
    final static public String DEFAULT_OUTPUT_MAIL = "jr-output-mail-default.json";
    final static public String DEFAULT_CONNECTION = "jr-connection-default.json";
    final static public String DEFAULT_TEMPLATE_SOURCE_DB = "jr-template-source-db-default.json";


    static private JROutputMail jrOutputMail;
    static private JRConnection jrConnection;
    static private JRTemplateSourceDB jrTemplateSourceDB;
    static private JRBeforeReportAction jrBeforeReportAction;

    static <T> T checkIfNullThenDefault(T obj, T defaultObj) {
        return obj != null ? obj : defaultObj;
    }

 
    public static JROutputMail getJrOutputMail() {
        return jrOutputMail;
    }

    public static void setJrConnection(JRConnection jrConnection) {
        JRDefaultContext.jrConnection = jrConnection;
    }

    public static void setDefaultTemplateDBExtractor(TemplateDBExtractor templateDBExtractor) {
        jrTemplateSourceDB = JRTemplateSourceDB.custom(jrTemplateSourceDB).withTemplateDBExtractor(templateDBExtractor).build();
    }

    public static void setJrOutputMail(JROutputMail jrOutputMail) {
        JRDefaultContext.jrOutputMail = jrOutputMail;
    }

    public static JRConnection getJrConnection() {
        return jrConnection;
    }

    public static void setJrTemplateSourceDB(JRTemplateSourceDB jrTemplateSourceDB) {
        JRDefaultContext.jrTemplateSourceDB = jrTemplateSourceDB;
    }


    public static JRBeforeReportAction getJrBeforeReportAction() {
        return jrBeforeReportAction;
    }

    public static void setJrBeforeReportAction(JRBeforeReportAction jrBeforeReportAction) {
        JRDefaultContext.jrBeforeReportAction = jrBeforeReportAction;
    }

    public static JRTemplateSourceDB getJrTemplateSourceDB() {
        return jrTemplateSourceDB;
    }

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //JROutput jrOutput = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_OUTPUT_MAIL), JROutputMail.class);
            jrOutputMail = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_OUTPUT_MAIL), JROutputMail.class);
            jrConnection = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_CONNECTION), JRConnection.class);
            jrTemplateSourceDB = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_TEMPLATE_SOURCE_DB), JRTemplateSourceDB.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
