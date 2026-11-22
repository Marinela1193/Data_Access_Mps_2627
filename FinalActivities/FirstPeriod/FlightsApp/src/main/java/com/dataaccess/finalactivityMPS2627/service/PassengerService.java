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

    //we declare a variable to check that the phone number is valid
    private static final String PHONE_REGEX = "^[0-9+\\-]{1,12}$";

    //method to add a passenger through an XML file and using the SAX method
    public static void addPassenger(String filename) {

        //we declare the file that we are about ot read and that has been passed by the parameter
        File file = new File(filename);

        //if the file does not exist we show it
        if (!file.exists()) {
            System.out.println("Error: File does not exist");
            LoggerService.log("Error: XML file not found");
            return;
        }

        //we declare a list of passenger to store the information read from the file
        List<Passenger> list = new ArrayList<>();
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();

            PassengerHandler handler = new PassengerHandler();

            saxParser.parse(file, handler);

            //we introduce the information of our file in the list thought the method getPassengers of our handler
            list = handler.getPassengers();

        } catch (Exception e) {
            System.out.println("Error reading XML file: " + e.getMessage());
            LoggerService.log("Error parsing XML file: " + e.getMessage());
            return;
        }

            //if the list is empty, and has no information
            if (list.isEmpty()) {
                System.out.println("Error: No passengers found in the XML file.");
                LoggerService.log("No passengers found in XML file: " + filename);
                return;
            }

            //if we have information then we use a loop to retrieve every passenger of the list and add it to our BD
            for (Passenger p : list) {
                //if any of the compulsory fields is empty it will lead to error
                if (p.getPassportNo() == null || p.getPassportNo().trim().isEmpty() ||
                        p.getFirstName() == null || p.getFirstName().trim().isEmpty() ||
                        p.getLastName() == null || p.getLastName().trim().isEmpty()) {

                    System.out.println("Error: Missing mandatory fields for passenger.");
                    LoggerService.log("Error: mandatory fields missing.");
                    return;
                }

                //if they are not empty then we check they have been written fine
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

            //we declare our consultation to introduce the passengers in our BD
        String sql = "INSERT INTO passengers (passportno, firstname, lastname, address, phone) VALUES (?, ?, ?, ?, ?)";

            //we establish the connection
        try (Connection conn = ConnectionManager.getConnection()) {
            //we deactivate the autosave
            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                for (Passenger p : list) {
                    //we save in our BD the parameters we retrieve from our list
                    pst.setString(1, p.getPassportNo().trim().toUpperCase());
                    pst.setString(2, p.getFirstName().trim());
                    pst.setString(3, p.getLastName().trim());

                    //we check if any of the non compulsory parameters is empty in order to add it as null
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
                // once we run our consultation we save it with commit
                conn.commit();
                System.out.println("Passengers inserted successfully");
                LoggerService.log("Success passengers imported.");

            } catch (SQLException ex) {
                //in case an error is catch, we will not save the data retrieved form our consult
                conn.rollback();
                System.out.println("Transaction rolled back");
                LoggerService.log("Rollback executed");
            }finally{
                //when we finish we will activate again the autosaving
                conn.setAutoCommit(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            LoggerService.log("Database connection error: " + e.getMessage());
            }
        }
    }

