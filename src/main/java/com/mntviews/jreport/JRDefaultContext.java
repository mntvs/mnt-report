package com.mntviews.jreport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Represents default context loaded on the initialization. Each object can be configured by corresponding json file
 * for each part of the config
 */
public class JRDefaultContext {

    private JRDefaultContext() {}
    public static final String PATH = "jr-default/";
    public static final String DEFAULT_OUTPUT_MAIL = "jr-output-mail-default.json";
    public static final String DEFAULT_CONNECTION = "jr-connection-default.json";
    public static final String DEFAULT_TEMPLATE_SOURCE_DB = "jr-template-source-db-default.json";
    public static final String DEFAULT_REPORT = "jr-report-default.json";
    public static final String DEFAULT_PARAM_LIST = "jr-param-list-default.json";

    private static JROutputMail jrOutputMail;
    private static JRConnection jrConnection;
    private static JRTemplateSourceDB jrTemplateSourceDB;
    private static JRBeforeReportAction jrBeforeReportAction;
    private static List<JRParam> jrParamList;

    public static List<JRParam> getJrParamList() {
        return jrParamList;
    }

    public static JRReport getJrReport() {
        return jrReport;
    }

    public static void setJrReport(JRReport jrReport) {
        JRDefaultContext.jrReport = jrReport;
    }

    private static JRReport jrReport;
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
        jrTemplateSourceDB = JRTemplateSourceDB.custom(jrTemplateSourceDB).templateDBExtractor(templateDBExtractor).build();
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
            jrOutputMail = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_OUTPUT_MAIL), JROutputMail.class);
            jrConnection = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_CONNECTION), JRConnection.class);
            jrTemplateSourceDB = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_TEMPLATE_SOURCE_DB), JRTemplateSourceDB.class);
            jrReport = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_REPORT), JRReport.class);

            jrParamList = objectMapper.readValue(JRDefaultContext.class.getClassLoader().getResourceAsStream(PATH + DEFAULT_PARAM_LIST), new TypeReference<List<JRParam>>(){});

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
