package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//Using the ‘employees’ database, create a new stored procedure to list all
//the employees on a specific job
public class Ejercicio744 {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {

        boolean found = false;

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Search by job");
        System.out.println("2. Search by depto");
        System.out.println("3. Search by name");

        int option = sc.nextInt();
        sc.nextLine();

        try(Connection conn = ConnectionManager.getConnection()){
            CallableStatement statement = null;
            if(option==1){
                System.out.println("Write the job you would like to search for");
                String job = sc.nextLine();

                statement = conn.prepareCall( "{call set_of_employees(?)}");
                statement.setString(1,job);

            }else if(option==2) {
                System.out.println("Write the department you would like to search for");
                String depto = sc.nextLine();

                statement = conn.prepareCall("{call employees_by_dept(?)}");
                statement.setString(1, depto);

            }else if(option==3) {
                System.out.println("Write the pattern you would like to search for");
                String pattern = sc.nextLine();

                statement = conn.prepareCall( "{call employees_by_pattern(?)}");
                statement.setString(1, pattern);

            }else{
                System.out.println("The option is not valid");
                return;
            }
            ResultSet rs = statement.executeQuery();
            System.out.println("Code" + "\t" + "Name");
            System.out.println("-----------------------------------------");

            while (rs.next()) {
                found = true;
                System.out.println(rs.getString(1) + "\t "
                        + rs.getString(2));
            }

            if (!found) {
                System.out.println("There are no matches for this query");
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
