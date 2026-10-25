package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/" +
                "VTInstitute";
        String user = "postgres";
        String password = "POSTGRES";

        try {
            Class.forName("org.postgresql.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, password)){
                System.out.println("Connection established!!!");
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Cannot load PostgreSQL Driver!!");
        }
        catch (SQLException e) {
            System.out.println("Connection couldn't be established!");
        }
    }
}
