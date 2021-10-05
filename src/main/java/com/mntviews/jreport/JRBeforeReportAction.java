package com.mntviews.jreport;

import java.sql.Connection;
import java.sql.SQLException;

public interface JRBeforeReportAction {
    void excecute(Connection connection) throws SQLException;
}
