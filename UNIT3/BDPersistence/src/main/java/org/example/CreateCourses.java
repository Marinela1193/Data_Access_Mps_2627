package org.example;

import java.sql.*;
import java.util.Scanner;

public class CreateCourses {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "POSTGRES";

        String SQLsentence = "CREATE TABLE courses (" +
                "code SERIAL PRIMARY KEY, " +
                "name VARCHAR (90))";
        String insertSQL = "INSERT INTO courses (name)  VALUES (?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement createPstmt  = con.prepareStatement(SQLsentence);
             PreparedStatement pstmt = con.prepareStatement(insertSQL))
        {
            createPstmt.executeUpdate();
            System.out.println("Table courses created correctly");

            pstmt.setString(1, "MULTIPLATFORM APP DEVELOPMENT");
            pstmt.executeUpdate();

            pstmt.setString(1, "WEB DEVELOPEMENT");
            pstmt.executeUpdate();

        }catch (SQLException e) {
            System.out.println("Error adding the courses:");
            System.out.println(e.getMessage());        }
        }

    }
