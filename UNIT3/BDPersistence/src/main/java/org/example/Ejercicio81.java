package org.example;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio81 {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {

        String insertSQL = "INSERT INTO employee (ename, job, deptno)  VALUES (?, ?, ?)";

        String createSQL = "INSERT INTO dept (dname, loc) VALUES (?, ?)";

        String searchDeptSQL = "SELECT deptno FROM dept WHERE dname ILIKE  ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS);
             Scanner sc = new Scanner(System.in))
        {
             conn.setAutoCommit(false);

             try {


                System.out.println("Write the name of the employee you would like to add");
                String name = sc.nextLine();
                pstmt.setString(1, name);


                System.out.println("Write the name of the job this employee performs");
                String job = sc.nextLine();
                pstmt.setString(2, job);


                System.out.println("Write the name of the department this employee belongs to");
                String dept = sc.nextLine();

                int deptCode;

                try(PreparedStatement searchDept =
                            conn.prepareStatement(searchDeptSQL)) {

                    searchDept.setString(1, dept);
                    ResultSet rs = searchDept.executeQuery();



                    if(rs.next()) {
                        deptCode = rs.getInt(1);
                    }else{

                        try(PreparedStatement pstmt1 = conn.prepareStatement(createSQL,Statement.RETURN_GENERATED_KEYS)){

                            //pstmt1.setInt(1, deptoCode);

                            pstmt1.setString(1, dept);

                            System.out.println("Write the place of the department");
                            String place = sc.nextLine();
                            pstmt1.setString(2, place);

                            pstmt1.executeUpdate();

                            ResultSet keysDepto = pstmt1.getGeneratedKeys();
                            keysDepto.next();
                            deptCode = keysDepto.getInt(1);

                        }
                    }
                }

                pstmt.setInt(3, deptCode);
                pstmt.executeUpdate();

                 ResultSet keys =
                         pstmt.getGeneratedKeys();

                 keys.next();

                 int employeeCode =
                         keys.getInt(1);
                 System.out.println("Employee created with code: " + employeeCode);

                 System.out.println("Transaction finished correctly!");
                 conn.commit();

            }catch (SQLException e){
                System.out.println(e.getMessage());
                conn.rollback();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
