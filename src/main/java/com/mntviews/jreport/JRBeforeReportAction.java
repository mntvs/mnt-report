package com.mntviews.jreport;

import java.sql.Connection;
import java.sql.SQLException;

public interface JRBeforeReportAction {
    void execute(Connection connection) throws SQLException;
}
