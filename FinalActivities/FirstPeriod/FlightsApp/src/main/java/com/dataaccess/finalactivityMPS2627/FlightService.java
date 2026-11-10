package com.dataaccess.finalactivityMPS2627;

import java.sql.*;

public class FlightService {
    public static void searchFlights(String origin, String destination, String date) {

        if (origin == null || origin.trim().isEmpty() ||
                destination == null || destination.trim().isEmpty() ||
                date == null || date.trim().isEmpty()) {
            System.out.println("Origin or Destination or Date is empty");
            LoggerService.log("Origin or Destination or Date is empty");
            return;
        }else {

            String sql = "SELECT * FROM fn_showflights_mps(?, ?, ?)";

            try (Connection conn = ConnectionManager.getConnection()) {
                try(PreparedStatement pstm = conn.prepareStatement(sql)) {
                    pstm.setString(1, origin.toUpperCase());
                    pstm.setString(2, destination.toUpperCase());
                    pstm.setDate(3, Date.valueOf(date));

                    try(ResultSet rs = pstm.executeQuery()) {
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
                        }
                        else {
                            LoggerService.log("Success searchFlights");
                        }
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
