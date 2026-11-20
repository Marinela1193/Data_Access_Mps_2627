package com.dataaccess.finalactivityMPS2627.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String PROPERTIES_FILE = "database.properties";
    private static final String PROPERTIES_URL = "db.url";
    private static final String PROPERTIES_USER = "db.user";
    private static final String PROPERTIES_PASSWORD = "db.password";
    private static final String PROPERTIES_SCHEMA = "db.schema";
    private static final String DEFAULT_SCHEMA = "public";

    static Connection connection = null;

    private ConnectionManager() {}

    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName(DRIVER);

            Properties properties = new Properties();
            try {
                InputStream propertiesStream = ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE);
                if (propertiesStream != null) {
                    properties.load(propertiesStream);
                    propertiesStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error leyendo " + PROPERTIES_FILE + ": " + e.getMessage());
            }

            String url = properties.getProperty(PROPERTIES_URL);
            String user = properties.getProperty(PROPERTIES_USER);
            String password = properties.getProperty(PROPERTIES_PASSWORD);
            String schema = properties.getProperty(PROPERTIES_SCHEMA, DEFAULT_SCHEMA);

            connection = DriverManager.getConnection(url, user, password);
            if (schema != null && !schema.trim().isEmpty()) {
                connection.setSchema(schema.trim());
            }
        }
        return connection;
    }
}
