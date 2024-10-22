package com.pancotti.henrique.sales.management.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String CONN_URI = "jdbc:mysql://localhost:3306/DBSALES";
    private static final String CONN_USER = "sales-management";
    private static final String CONN_PASSWORD = "sales-management@2024";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(CONN_URI, CONN_USER, CONN_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
