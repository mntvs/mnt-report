package com.mntviews.jreport;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Contract for implementing by database related object
 */
public interface Connectable {
    Connection createConnection(String url, String userName, String password) throws SQLException;
}
