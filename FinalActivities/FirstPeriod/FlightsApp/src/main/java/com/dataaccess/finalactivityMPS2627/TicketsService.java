package com.dataaccess.finalactivityMPS2627;

import java.sql.*;
import java.sql.Date;

public class TicketsService {
    public static void buyTickets(String customer_id, String flight_code, String date) {

        if (customer_id == null || customer_id.trim().isEmpty() ||
                flight_code == null || flight_code.trim().isEmpty() ||
                date == null || date.trim().isEmpty()) {
            System.out.println("Customer id, the flight id and the date is empty, it must be filled.");
            LoggerService.log("Customer id, the flight id and the date is empty, it must be filled.");
            return;
        } else {

            String sqlCheckPassenger = "SELECT t.ticket_number " +
                    "FROM tickets t " +
                    "WHERE t.passportno = ? AND t.date_of_travel = ? " +
                    "AND t.date_of_cancellation IS NULL";

            String sqlTotalSeats = "SELECT p.total_seats FROM flights f " +
                    "INNER JOIN planes p ON f.plane = p.plane_id " +
                    "WHERE f.flight_code = ?";

            String sqlSoldTickets = "SELECT COUNT (*) AS sold_tickets FROM tickets t " +
                    "WHERE t.flight_code = ? AND t.date_of_travel = ? " +
                    "AND t.date_of_cancellation IS NULL";


            try (Connection conn = ConnectionManager.getConnection()) {
                Date travelDate = Date.valueOf(date);

                try (PreparedStatement psmt = conn.prepareStatement(sqlCheckPassenger)) {
                    psmt.setString(1, customer_id);
                    psmt.setDate(2, travelDate);

                    try (ResultSet rs = psmt.executeQuery()) {

                        if (rs.next()) {
                            System.out.println("Customer id: " + customer_id + " cannot buy another ticket for this date");
                            LoggerService.log("Customer id: " + customer_id + " cannot buy another ticket for this date");
                            return;
                        }
                    }
                }

                int totalSeats = 0;

                try (PreparedStatement psSeats = conn.prepareStatement(sqlTotalSeats)) {

                    psSeats.setString(1, flight_code);
                    try (ResultSet rsSeats = psSeats.executeQuery()) {
                        if (rsSeats.next()) {
                            totalSeats = rsSeats.getInt(1);
                        } else {
                            System.out.println("Error: Flight " + flight_code + " does not exist.");
                            LoggerService.log("Error: Flight " + flight_code + " does not exist.");
                            return;
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int soldTickets = 0;

                try (PreparedStatement psTickets = conn.prepareStatement(sqlSoldTickets)) {
                    psTickets.setString(1, flight_code);
                    psTickets.setDate(2, travelDate);

                    try (ResultSet rsTickets = psTickets.executeQuery()) {
                        if (rsTickets.next()) {
                            soldTickets = rsTickets.getInt("sold_tickets");
                        } else {
                            System.out.println("Error: Flight " + flight_code + " does not exist.");
                            LoggerService.log("Error: Flight " + flight_code + " does not exist.");
                            return;
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int availableSeats = totalSeats - soldTickets;
                if(availableSeats <= 0) {
                    System.out.println("Error: No available seats left on flight " + flight_code + " for date " + date);
                    LoggerService.log("No seats available for flight " + flight_code + " on " + date);
                    return;
                }else{
                    //we sell the ticket
                    String sqlSellTicket = "INSERT INTO tickets (passportno, flight_code, date_of_booking, date_of_travel ) VALUES (?, ?, CURRENT_DATE, ?)";

                    conn.setAutoCommit(false);

                    try(PreparedStatement psSellTicket = conn.prepareStatement(sqlSellTicket)) {
                        psSellTicket.setString(1, customer_id);
                        psSellTicket.setString(2, flight_code);
                        psSellTicket.setDate(3, travelDate);

                        int rowsAffected = psSellTicket.executeUpdate();

                        if (rowsAffected > 0) {
                            conn.commit();
                            System.out.println("Ticket successfully purchased for customer " + customer_id + " on flight " + flight_code);
                            LoggerService.log("Ticket bought: Customer " + customer_id + ", Flight " + flight_code + ", Date " + date);
                        } else {
                            conn.rollback();
                            System.out.println("Error: The ticket could not be purchased.");
                            LoggerService.log("Failed to buy ticket: No rows affected.");
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
