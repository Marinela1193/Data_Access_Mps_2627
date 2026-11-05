package com.dataaccess.finalactivityMPS2627;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    static Connection connection = null;

    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/flights2627";
            String user = "postgres";
            String password = "POSTGRES";
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
