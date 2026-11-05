package com.dataaccess.finalactivityMPS2627;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PassengerService {
    public static void main(String filename) {

        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("Error: File doest not exist");
            LoggerService.log("Error: XML file not found");
            return;
        }

        List<Passenger> list = new ArrayList<>();
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();

            PassengerHandler handler = new PassengerHandler();

            saxParser.parse(file, handler);

            list = handler.getPassengers();

            if (list.isEmpty()) {
                System.out.println("No passengers found");
                return;
            }

            for (Passenger p : list) {
                if (p.getPassportNo() == null || p.getPassportNo().isEmpty() ||
                        p.getFirstName() == null || p.getFirstName().isEmpty() ||
                        p.getLastName() == null || p.getLastName().isEmpty()) {

                    System.out.println("Error: Missing mandatory fields for passenger.");
                    LoggerService.log("Error: mandatory fields missing.");
                    return;
                }

                if (p.getPassportNo().length() > 10) {
                    System.out.println("Error: Passport format incorrect");
                    LoggerService.log("Error: Passport format incorrect");
                    return;
                }

                if (p.getPhone() != null && !p.getPhone().isEmpty() && p.getPhone().length() > 12) {
                    System.out.println("Error: Phone format incorrect");
                    LoggerService.log("Eerror: Phone format incorrect");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO passengers (passportno, firstname, lastname, address, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                for (Passenger p : list) {
                    pst.setString(1, p.getPassportNo());
                    pst.setString(2, p.getFirstName());
                    pst.setString(3, p.getLastName());

                    if (p.getAddress() == null || p.getAddress().isEmpty()) {
                        pst.setNull(4, Types.VARCHAR);
                    } else {
                        pst.setString(4, p.getAddress());
                    }
                    if (p.getPhone() == null || p.getPhone().isEmpty()) {
                        pst.setNull(5, Types.VARCHAR);
                    } else {
                        pst.setString(5, p.getPhone());
                    }

                    pst.executeUpdate();
                }
                conn.commit();
                System.out.println("Passengers inserted successfully");
                LoggerService.log("Success passengers imported.");

            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Transaction rolled back");
                LoggerService.log("Rollback executed");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Database connection error: " + e.getMessage());
            }
        }
    }

