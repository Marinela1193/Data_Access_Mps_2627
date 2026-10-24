package org.example;

import java.sql.*;

public class ConsultaBD1 {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "POSTGRES";
        Connection con = DriverManager.getConnection(url, user, password);
        Statement statement = con.createStatement();
        String SQLsentence = "SELECT * FROM subjects ORDER BY code";
        ResultSet rs = statement.executeQuery(SQLsentence);
        System.out.println("Code" + "\t" + "Name" + "\t" + "YEAR" + "\t" + "HOURS");
        System.out.println("-----------------------------------------");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t " +
                    rs.getString(2) + "\t " + rs.getString(3) +
                    "\t" + rs.getString(4)  );
        }

        String insertSQL = "INSERT INTO subjects (name, year)" +
                "VALUES ('MARKUP LANGUAGES', 1)";
        int rowsAffected = statement.executeUpdate(insertSQL);

        System.out.println("Rows affected: " + rowsAffected);

        try {
            String alterSQL = "ALTER TABLE subjects ADD COLUMN hours INT";
            boolean result = statement.execute(alterSQL);
            System.out.println("Column added to the table correctly");

        } catch (SQLException e) {
            System.out.println("Error adding column:");
            System.out.println(e.getMessage());
        }

        rs.close();
        con.close();
    }
}
