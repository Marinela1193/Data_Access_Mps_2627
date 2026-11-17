package com.dataaccess.finalactivityMPS2627.service;

import com.dataaccess.finalactivityMPS2627.util.ConnectionManager;

import java.sql.*;

public class FlightService {
    public static void searchFlights(String origin, String destination, String date) {

        if (origin == null || origin.trim().isEmpty() ||
                destination == null || destination.trim().isEmpty() ||
                date == null || date.trim().isEmpty()) {
            System.out.println("Origin or Destination or Date is empty");
            LoggerService.log("Origin or Destination or Date is empty");
            return;
        } else {

            Date travelDate;
            try {
                travelDate = Date.valueOf(date.trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid date format. Expected YYYY-MM-DD.");
                LoggerService.log("Invalid date format in searchFlights: " + date);
                return;
            }

            String sql = "SELECT * FROM fn_showflights_mps(?, ?, ?)";

            try (Connection conn = ConnectionManager.getConnection()) {
                try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                    pstm.setString(1, origin.toUpperCase());
                    pstm.setString(2, destination.toUpperCase());
                    pstm.setDate(3, travelDate);

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
                            System.out.println("No flights found for " + origin + " -> " + destination + " on " + date);
                        } else {
                            LoggerService.log("Success searchFlights");
                        }
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Database error: " + e.getMessage());
                LoggerService.log("Error in searchFlights: " + e.getMessage());
            }
        }
    }

    public static void searchOrigins() {
        try {
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

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Error in searchOrigins: " + e.getMessage());
        }

    }

    public static void showDestinations(String origin) {
        if (origin == null || origin.trim().isEmpty()) {
            System.out.println("Origin airport is empty");
            LoggerService.log("Origin airport is empty");
        } else {
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

}
