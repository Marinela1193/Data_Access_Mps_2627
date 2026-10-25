package org.example;

import java.sql.*;
import java.util.Scanner;

public class consultaBD1PreparedStatements {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "POSTGRES";

        String SQLsentence = "SELECT * FROM subjects ORDER BY code";
        String insertSQL = "INSERT INTO subjects (name, year)  VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement statement = con.createStatement();
             PreparedStatement pstmt = con.prepareStatement(insertSQL);
             Scanner scanner = new Scanner(System.in);
             ResultSet rs = statement.executeQuery(SQLsentence)) {
            System.out.println("Code" + "\t" + "Name" + "\t" + "YEAR" + "\t" + "HOURS");
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t " +
                        rs.getString(2) + "\t " + rs.getString(3) +
                        "\t" + rs.getString(4));

            }

            while (true) {
                System.out.println("Enter the name of the subject to insert");
                String name = scanner.nextLine();

                if (name.isEmpty()) {
                    break;
                }

                System.out.println("Enter the year of the subject to insert");
                int year = scanner.nextInt();
                scanner.nextLine();

                pstmt.setString(1, (name));
                pstmt.setInt(2, (year));

                pstmt.executeUpdate();
            }

            /*try {
                String alterSQL = "ALTER TABLE subjects ADD COLUMN hours INT";
                boolean result = statement.execute(alterSQL);
                System.out.println("Column added to the table correctly");

            } catch (SQLException e) {
                System.out.println("Error adding column:");
                System.out.println(e.getMessage());
            }*/

        }catch(SQLException e){
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
