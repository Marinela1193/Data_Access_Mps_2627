package com.dataaccess.finalactivityMPS2627;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    static Connection connection = null;

    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName("org.postgresql.Driver");

            Properties properties = new Properties();
            try {
                InputStream propertiesStream = ClassLoader.getSystemResourceAsStream("db.properties");
                if (propertiesStream != null) {
                    properties.load(propertiesStream);
                    propertiesStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error leyendo db.properties: " + e.getMessage());
            }

            String url = "jdbc:postgresql://localhost:5432/flights2627";
            String user = "postgres";
            String password = "POSTGRES";
            String schema = "public";
            connection = DriverManager.getConnection(url, user, password);
            connection.setSchema(schema);
        }
        return connection;
    }
}
