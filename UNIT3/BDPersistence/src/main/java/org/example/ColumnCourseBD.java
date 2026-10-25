package org.example;

import java.sql.*;

public class ColumnCourseBD {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "POSTGRES";

        String SQLsentence = "ALTER TABLE subjects ADD COLUMN course INT ";

        String ForeignKeySentence = "ALTER TABLE subjects " +
                "ADD CONSTRAINT fk_subject_course " +
                "FOREIGN KEY (course) " +
                "REFERENCES courses(code)";

        String updateSQL = "UPDATE subjects SET course = 1 WHERE course IS NULL";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = con.prepareStatement(SQLsentence);
             PreparedStatement foreignPstmt = con.prepareStatement(ForeignKeySentence);
             PreparedStatement updatePstmt = con.prepareStatement(updateSQL))
        {

            pstmt.executeUpdate();
            updatePstmt.executeUpdate();
            foreignPstmt.executeUpdate();

            System.out.println("Column course added to table subjects");

        } catch (SQLException e) {
            System.out.println("Error adding the column to subjects");
            System.out.println(e.getMessage());
        }
    }
}
