package com.dataaccess.finalactivityMPS2627.service;

import com.dataaccess.finalactivityMPS2627.util.ConnectionManager;
import com.dataaccess.finalactivityMPS2627.util.validationClass;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

public class TicketsService {
    public static void buyTickets(String customer_id, String flight_code, String date) {

        if (!validationClass.validCustomerId(customer_id)) {
            System.out.println("Customer id, the flight id and the date is empty, it must be filled.");
            LoggerService.log("Customer id, the flight id and the date is empty, it must be filled.");
            return;
        }

        if (!validationClass.validFlightCode(flight_code)) {
            System.out.println("Flight code, the flight code is empty, it must be filled.");
            LoggerService.log("Flight code, the flight code is empty, it must be filled.");
            return;
        }

        LocalDate localDate = validationClass.correctDate(date);
        if (localDate == null) {
            System.out.println("Error: invalid date format. Expected YYYY-MM-DD format.");
            LoggerService.log("Invalid date format. Expected YYYY-MM-DD format.");
            return;
        }

        if (validationClass.pastDate(localDate)) {
            System.out.println("Error: You cannot buy a ticket for an already past date");
            LoggerService.log("You cannot buy a ticket for an already past date");
            return;
        }

        String cleanCustomerId = customer_id.trim().toUpperCase();
        String cleanFlightCode = flight_code.trim().toUpperCase();
        Date travelDate = Date.valueOf(localDate);

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

            try (PreparedStatement psmt = conn.prepareStatement(sqlCheckPassenger)) {
                psmt.setString(1, cleanCustomerId);
                psmt.setDate(2, travelDate);

                try (ResultSet rs = psmt.executeQuery()) {

                    if (rs.next()) {
                        System.out.println("Customer id: " + cleanCustomerId + " cannot buy another ticket for this date");
                        LoggerService.log("Customer id: " + cleanCustomerId + " cannot buy another ticket for this date");
                        return;
                    }
                }
            }

            int totalSeats = 0;

            try (PreparedStatement psSeats = conn.prepareStatement(sqlTotalSeats)) {

                psSeats.setString(1, cleanFlightCode);
                try (ResultSet rsSeats = psSeats.executeQuery()) {
                    if (rsSeats.next()) {
                        totalSeats = rsSeats.getInt(1);
                    } else {
                        System.out.println("Error: Flight " + cleanFlightCode + " does not exist.");
                        LoggerService.log("Error: Flight " + cleanFlightCode + " does not exist.");
                        return;
                    }
                }
            }

            int soldTickets = 0;

            try (PreparedStatement psTickets = conn.prepareStatement(sqlSoldTickets)) {
                psTickets.setString(1, cleanFlightCode);
                psTickets.setDate(2, travelDate);

                try (ResultSet rsTickets = psTickets.executeQuery()) {
                    if (rsTickets.next()) {
                        soldTickets = rsTickets.getInt("sold_tickets");
                    } else {
                        System.out.println("Error: Flight " + cleanFlightCode + " does not exist.");
                        LoggerService.log("Error: Flight " + cleanFlightCode + " does not exist.");
                        return;
                    }
                }

            }

            int availableSeats = totalSeats - soldTickets;
            if (availableSeats <= 0) {
                System.out.println("Error: No available seats left on flight " + cleanFlightCode + " for date " + travelDate);
                LoggerService.log("No seats available for flight " + cleanFlightCode + " on " + travelDate);
                return;
            } else {
                //we sell the ticket
                String sqlSellTicket = "INSERT INTO tickets (passportno, flight_code, date_of_booking, date_of_travel ) VALUES (?, ?, CURRENT_DATE, ?)";

                conn.setAutoCommit(false);

                try (PreparedStatement psSellTicket = conn.prepareStatement(sqlSellTicket)) {
                    psSellTicket.setString(1, cleanCustomerId);
                    psSellTicket.setString(2, cleanFlightCode);
                    psSellTicket.setDate(3, travelDate);

                    int rowsAffected = psSellTicket.executeUpdate();

                    if (rowsAffected > 0) {
                        conn.commit();
                        System.out.println("Ticket successfully purchased for customer " + cleanCustomerId + " on flight " + cleanFlightCode);
                        LoggerService.log("Ticket bought: Customer " + cleanCustomerId + ", Flight " + cleanFlightCode + ", Date " + travelDate);
                    } else {
                        conn.rollback();
                        System.out.println("Error: The ticket could not be purchased.");
                        LoggerService.log("Failed to buy ticket: No rows affected.");
                    }

                } catch (SQLException ex) {
                    conn.rollback();
                    System.out.println("Error purchasing ticket: " + ex.getMessage());
                    LoggerService.log("Rollback in buyTickets: " + ex.getMessage());
                } finally {
                    conn.setAutoCommit(true);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
