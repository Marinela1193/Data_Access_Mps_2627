package com.dataaccess.finalactivityMPS2627.service;

import com.dataaccess.finalactivityMPS2627.util.ConnectionManager;
import com.dataaccess.finalactivityMPS2627.util.PassengerHandler;
import com.dataaccess.finalactivityMPS2627.model.Passenger;
import com.dataaccess.finalactivityMPS2627.util.ValidationClass;

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

    private static final String PHONE_REGEX = "^[0-9+\\-]{1,12}$";

    public static void addPassenger(String filename) {

        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("Error: File does not exist");
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
        } catch (Exception e) {
            System.out.println("Error reading XML file: " + e.getMessage());
            LoggerService.log("Error parsing XML file: " + e.getMessage());
            return;
        }

            if (list.isEmpty()) {
                System.out.println("Error: No passengers found in the XML file.");
                LoggerService.log("No passengers found in XML file: " + filename);
                return;
            }

            for (Passenger p : list) {
                if (p.getPassportNo() == null || p.getPassportNo().trim().isEmpty() ||
                        p.getFirstName() == null || p.getFirstName().trim().isEmpty() ||
                        p.getLastName() == null || p.getLastName().trim().isEmpty()) {

                    System.out.println("Error: Missing mandatory fields for passenger.");
                    LoggerService.log("Error: mandatory fields missing.");
                    return;
                }

                if (!ValidationClass.validCustomerId(p.getPassportNo())) {
                    System.out.println("Error: Passport format incorrect");
                    LoggerService.log("Error: Passport format incorrect");
                    return;
                }

                if (p.getPhone() != null && !p.getPhone().trim().isEmpty() && !p.getPhone().trim().matches(PHONE_REGEX)) {
                    System.out.println("Error: Phone format incorrect");
                    LoggerService.log("Error: Phone format incorrect");
                    return;
                }
            }

        String sql = "INSERT INTO passengers (passportno, firstname, lastname, address, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                for (Passenger p : list) {
                    pst.setString(1, p.getPassportNo().trim().toUpperCase());
                    pst.setString(2, p.getFirstName().trim());
                    pst.setString(3, p.getLastName().trim());

                    if (p.getAddress() == null || p.getAddress().trim().isEmpty()) {
                        pst.setNull(4, Types.VARCHAR);
                    } else {
                        pst.setString(4, p.getAddress().trim());
                    }
                    if (p.getPhone() == null || p.getPhone().trim().isEmpty()) {
                        pst.setNull(5, Types.VARCHAR);
                    } else {
                        pst.setString(5, p.getPhone().trim());
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
            }finally{
                conn.setAutoCommit(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Database connection error: " + e.getMessage());
            }
        }
    }

