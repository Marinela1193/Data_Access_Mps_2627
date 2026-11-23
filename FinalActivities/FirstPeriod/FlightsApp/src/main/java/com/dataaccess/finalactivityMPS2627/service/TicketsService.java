package com.dataaccess.finalactivityMPS2627.service;

import com.dataaccess.finalactivityMPS2627.util.ConnectionManager;
import com.dataaccess.finalactivityMPS2627.util.ValidationClass;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

public class TicketsService {
    public static void buyTickets(String customer_id, String flight_code, String date) {

        //we validate the parameters received
        if (!ValidationClass.validCustomerId(customer_id)) {
            System.out.println("Customer id, the flight id and the date is empty, it must be filled.");
            LoggerService.log("Customer id, the flight id and the date is empty, it must be filled.");
            return;
        }

        if (!ValidationClass.validFlightCode(flight_code)) {
            System.out.println("Flight code, the flight code is empty, it must be filled.");
            LoggerService.log("Flight code, the flight code is empty, it must be filled.");
            return;
        }

        //we declare our date in order to validate it
        LocalDate localDate = ValidationClass.correctDate(date);
        if (localDate == null) {
            System.out.println("Error: invalid date format. Expected YYYY-MM-DD format.");
            LoggerService.log("Invalid date format. Expected YYYY-MM-DD format.");
            return;
        }

        //we check the date is not a past date
        if (ValidationClass.pastDate(localDate)) {
            System.out.println("Error: You cannot buy a ticket for an already past date");
            LoggerService.log("You cannot buy a ticket for an already past date");
            return;
        }

        //we declare new variables to save the parameters recieved in upper case and clean
        String cleanCustomerId = customer_id.trim().toUpperCase();
        String cleanFlightCode = flight_code.trim().toUpperCase();
        Date travelDate = Date.valueOf(localDate);

        //we declare our consultations to the BD
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

        //we connect to our BD
        try (Connection conn = ConnectionManager.getConnection()) {

            try (PreparedStatement psmt = conn.prepareStatement(sqlCheckPassenger)) {
                psmt.setString(1, cleanCustomerId);
                psmt.setDate(2, travelDate);

                try (ResultSet rs = psmt.executeQuery()) {

                    //we check that, if the customer appears in our consultation it means he/she has already bought
                    //a ticket so he/she cannot buy a new ticket
                    if (rs.next()) {
                        System.out.println("Customer id: " + cleanCustomerId + " cannot buy another ticket for this date");
                        LoggerService.log("Customer id: " + cleanCustomerId + " cannot buy another ticket for this date");
                        return;
                    }
                }
            }

            //we declare a variable to save the total seats of a plane
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

            //we declare a variable to save the total number of tickets sold
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

            //we declare a new variable that will let us know if there are still seats available in the plane
            //to buy a ticket
            int availableSeats = totalSeats - soldTickets;
            if (availableSeats <= 0) {
                System.out.println("Error: No available seats left on flight " + cleanFlightCode + " for date " + travelDate);
                LoggerService.log("No seats available for flight " + cleanFlightCode + " on " + travelDate);
                return;
            } else {
                //we sell the ticket
                String sqlSellTicket = "INSERT INTO tickets (passportno, flight_code, date_of_booking, date_of_travel ) VALUES (?, ?, CURRENT_DATE, ?)";

                //before saving the consultation and the ticket bought we will deactivate the autocommit
                conn.setAutoCommit(false);

                try (PreparedStatement psSellTicket = conn.prepareStatement(sqlSellTicket)) {
                    psSellTicket.setString(1, cleanCustomerId);
                    psSellTicket.setString(2, cleanFlightCode);
                    psSellTicket.setDate(3, travelDate);

                    //to check that it has been added to our BD we declare a new variable
                    int rowsAffected = psSellTicket.executeUpdate();

                    //if the new variable is 1 or more then it means the ticket has been added to the DB
                    if (rowsAffected > 0) {
                        //then we save it
                        conn.commit();
                        System.out.println("Ticket successfully purchased for customer " + cleanCustomerId + " on flight " + cleanFlightCode);
                        LoggerService.log("Ticket bought: Customer " + cleanCustomerId + ", Flight " + cleanFlightCode + ", Date " + travelDate);
                    } else {
                        //otherwise we do not save anything
                        conn.rollback();
                        System.out.println("Error: The ticket could not be purchased.");
                        LoggerService.log("Failed to buy ticket: No rows affected.");
                    }

                } catch (SQLException ex) {
                    conn.rollback();
                    System.out.println("Error purchasing ticket: " + ex.getMessage());
                    LoggerService.log("Rollback in buyTickets: " + ex.getMessage());
                } finally {
                    //we activate again the autocommit
                    conn.setAutoCommit(true);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Database error in buyTickets: " + e.getMessage());
        }
    }

    public static void buyTicketsAndPrint (String customer_id, String flight_code, String date) {

        //we validate the parameters received
        if (!ValidationClass.validCustomerId(customer_id)) {
            System.out.println("Customer id, the flight id and the date is empty, it must be filled.");
            LoggerService.log("Customer id, the flight id and the date is empty, it must be filled.");
            return;
        }

        if (!ValidationClass.validFlightCode(flight_code)) {
            System.out.println("Flight code, the flight code is empty, it must be filled.");
            LoggerService.log("Flight code, the flight code is empty, it must be filled.");
            return;
        }

        //we declare our date in order to validate it
        LocalDate localDate = ValidationClass.correctDate(date);
        if (localDate == null) {
            System.out.println("Error: invalid date format. Expected YYYY-MM-DD format.");
            LoggerService.log("Invalid date format. Expected YYYY-MM-DD format.");
            return;
        }

        //we check the date is not a past date
        if (ValidationClass.pastDate(localDate)) {
            System.out.println("Error: You cannot buy a ticket for an already past date");
            LoggerService.log("You cannot buy a ticket for an already past date");
            return;
        }

        //we declare new variables to save the parameters recieved in upper case and clean
        String cleanCustomerId = customer_id.trim().toUpperCase();
        String cleanFlightCode = flight_code.trim().toUpperCase();
        Date travelDate = Date.valueOf(localDate);

        //we declare our consultations to the BD
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

        //we connect to our BD
        try (Connection conn = ConnectionManager.getConnection()) {

            try (PreparedStatement psmt = conn.prepareStatement(sqlCheckPassenger)) {
                psmt.setString(1, cleanCustomerId);
                psmt.setDate(2, travelDate);

                try (ResultSet rs = psmt.executeQuery()) {

                    //we check that, if the customer appears in our consultation it means he/she has already bought
                    //a ticket so he/she cannot buy a new ticket
                    if (rs.next()) {
                        System.out.println("Customer id: " + cleanCustomerId + " cannot buy another ticket for this date");
                        LoggerService.log("Customer id: " + cleanCustomerId + " cannot buy another ticket for this date");
                        return;
                    }
                }
            }

            //we declare a variable to save the total seats of a plane
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

            //we declare a variable to save the total number of tickets sold
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

            //we declare a new variable that will let us know if there are still seats available in the plane
            //to buy a ticket
            int availableSeats = totalSeats - soldTickets;
            if (availableSeats <= 0) {
                System.out.println("Error: No available seats left on flight " + cleanFlightCode + " for date " + travelDate);
                LoggerService.log("No seats available for flight " + cleanFlightCode + " on " + travelDate);
                return;
            } else {
                //we sell the ticket
                String sqlSellTicket = "INSERT INTO tickets (passportno, flight_code, date_of_booking, date_of_travel ) VALUES (?, ?, CURRENT_DATE, ?)";

                //before saving the consultation and the ticket bought we will deactivate the autocommit
                conn.setAutoCommit(false);

                try (PreparedStatement psSellTicket = conn.prepareStatement(sqlSellTicket)) {
                    psSellTicket.setString(1, cleanCustomerId);
                    psSellTicket.setString(2, cleanFlightCode);
                    psSellTicket.setDate(3, travelDate);

                    //to check that it has been added to our BD we declare a new variable
                    int rowsAffected = psSellTicket.executeUpdate();

                    //if the new variable is 1 or more then it means the ticket has been added to the DB
                    if (rowsAffected > 0) {
                        //then we save it
                        conn.commit();
                        System.out.println("Ticket successfully purchased for customer " + cleanCustomerId + " on flight " + cleanFlightCode);
                        LoggerService.log("Ticket bought: Customer " + cleanCustomerId + ", Flight " + cleanFlightCode + ", Date " + travelDate);

                        String sqlTicketNumber = "SELECT ticket_number FROM tickets WHERE passportno = ? AND flight_code = ?";

                        try(PreparedStatement psTicketNumber = conn.prepareStatement(sqlTicketNumber)) {
                            psTicketNumber.setString(1, cleanCustomerId);
                            psTicketNumber.setString(2, cleanFlightCode);

                            try (ResultSet rsTicketNumber = psTicketNumber.executeQuery()) {

                                if(rsTicketNumber.next()) {
                                    int ticketNumber = rsTicketNumber.getInt("ticket_number");

                                    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Ticket " + ticketNumber + false)))) {
                                        pw.print(cleanCustomerId);
                                        pw.print(",");
                                        pw.print(cleanFlightCode);
                                        pw.print(",");
                                        pw.print(travelDate);

                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }

                    } else {
                        //otherwise we do not save anything
                        conn.rollback();
                        System.out.println("Error: The ticket could not be purchased.");
                        LoggerService.log("Failed to buy ticket: No rows affected.");
                    }

                } catch (SQLException ex) {
                    conn.rollback();
                    System.out.println("Error purchasing ticket: " + ex.getMessage());
                    LoggerService.log("Rollback in buyTickets: " + ex.getMessage());
                } finally {
                    //we activate again the autocommit
                    conn.setAutoCommit(true);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Database error in buyTickets: " + e.getMessage());
        }
    }


    public static void cancelTicket(String customer_id, String date){

        if (!ValidationClass.validCustomerId(customer_id)) {
            System.out.println("Customer id, the flight id and the date is empty, it must be filled.");
            LoggerService.log("Customer id, the flight id and the date is empty, it must be filled.");
            return;
        }

        LocalDate localDate = ValidationClass.correctDate(date);
        if (localDate == null) {
            System.out.println("Error: invalid date format. Expected YYYY-MM-DD format.");
            LoggerService.log("Invalid date format. Expected YYYY-MM-DD format.");
            return;
        }

        String sql = "SELECT t.ticket_number, p.passportno FROM tickets t " +
                "INNER JOIN passengers p ON t.passportno = p.passportno " +
                "WHERE p.passportno = ? AND t.date_of_booking = ? AND t.date_of_cancellation IS NULL";

        String sql1 = "DELETE FROM tickets WHERE passportno = ? AND t.date_of_booking = ?";

        //ME FALTA EL UPDATE PARA HACER UPDATE EN LA FECHA DE CANCELACION

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmSelect = conn.prepareStatement(sql);
            PreparedStatement pstmDelete = conn.prepareStatement(sql1)){

            pstmSelect.setString(1,customer_id);
            pstmSelect.setDate(2,Date.valueOf(localDate));

            try(ResultSet rsSelect = pstmSelect.executeQuery()){
                if(rsSelect.next()) {
                    pstmDelete.setString(1, customer_id);
                    pstmDelete.setDate(2, Date.valueOf(localDate));

                    int rows = pstmDelete.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Ticket deleted successfully.");
                        LoggerService.log("Ticket deleted.");
                    } else {
                        System.out.println("Error: Ticket not found.");
                    }
                }else{
                    System.out.println("Error: Ticket number for this passenger and date not found.");
                    LoggerService.log("Ticket number for this passenger and date not found.");
                    return;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

}
