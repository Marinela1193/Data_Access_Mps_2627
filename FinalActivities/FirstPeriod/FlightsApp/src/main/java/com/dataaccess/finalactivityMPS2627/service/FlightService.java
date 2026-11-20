package com.dataaccess.finalactivityMPS2627.service;

import com.dataaccess.finalactivityMPS2627.util.ConnectionManager;
import com.dataaccess.finalactivityMPS2627.util.validationClass;

import java.sql.*;
import java.time.LocalDate;

public class FlightService {
    public static void searchFlights(String origin, String destination, String date) {

        if (!validationClass.validIata(origin) || !validationClass.validIata(destination)) {
            System.out.println("Error: Origin or Destination are not valid 3-letter code should be written");
            LoggerService.log("Error: Origin or Destination are not valid 3-letter code should be written");
            return;
        }

        LocalDate localDate = validationClass.correctDate(date);
        if (localDate == null) {
            System.out.println("Error: invalid date format. Expected YYYY-MM-DD format.");
            LoggerService.log("Error: invalid date format. Expected YYYY-MM-DD format.");
            return;
        }

        String sql = "SELECT * FROM fn_showflights_mps(?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, origin.toUpperCase());
            pstm.setString(2, destination.toUpperCase());
            pstm.setDate(3, Date.valueOf(localDate));

            try (ResultSet rs = pstm.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println("Flight: " + rs.getString(1));
                    System.out.println("From: " + rs.getString(2));
                    System.out.println("To: " + rs.getString(3));
                    System.out.println("Departure: " + rs.getString(4));
                    System.out.println("Arrival: " + rs.getString(5));
                    System.out.println("Duration: " + rs.getString(6));
                    System.out.println("Airline: " + rs.getString(7));
                    System.out.println("Distance: " + rs.getString(8));
                    System.out.println("Plane: " + rs.getString(9));
                    System.out.println("Free seats: " + rs.getString(10));
                    System.out.println("Date: " + rs.getString(11));
                }
                if (!found) {
                    System.out.println("No flights found for " + origin + " -> " + destination + " on " + localDate);
                    LoggerService.log("No Flights found for " + origin + " -> " + destination + " on " + localDate);
                } else {
                    LoggerService.log("Success searchFlights");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Error in searchFlights: " + e.getMessage());
        }
    }

    public static void searchOrigins() {
        String sql = "SELECT DISTINCT a.code, a.iata, a.name FROM flights f " +
                "INNER JOIN airports a ON f.source = a.code " +
                "ORDER BY a.name";
        ;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(rs.getString("iata") + " - " + rs.getString("name"));
            }
            if (!found) {
                System.out.println("There are no origins in the database");
                LoggerService.log("There are no origins in the database");
            } else {
                System.out.println("Success searchOrigins");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Error in searchOrigins: " + e.getMessage());
        }
    }

    public static void showDestinations(String origin) {
        if (!validationClass.validIata(origin)) {
            System.out.println("Error: Origin must be a valid 3-letter IATA code.");
            LoggerService.log("Error: invalid IATA code in showDestinations: " + origin);
            return;
        }

        String sql = "SELECT DISTINCT a_dest.code, a_dest.iata, a_dest.name " +
                "FROM flights f " +
                "INNER JOIN airports a_orig ON f.source = a_orig.code " +
                "INNER JOIN airports a_dest ON f.destination = a_dest.code " +
                "WHERE a_orig.iata = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, origin.toUpperCase());
            try (ResultSet rs = pstm.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(rs.getString("iata") + " - " + rs.getString("name"));
                }
                if (!found) {
                    System.out.println("No destinations found from origin: " + origin);
                    LoggerService.log("No destinations found from origin: " + origin);
                } else {
                    LoggerService.log("Success showDestinations for origin: " + origin);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Error in showDestinations: " + e.getMessage());
        }
    }

}
