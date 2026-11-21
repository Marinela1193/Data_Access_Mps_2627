package com.dataaccess.finalactivityMPS2627;

import com.dataaccess.finalactivityMPS2627.service.FlightService;
import com.dataaccess.finalactivityMPS2627.service.LoggerService;
import com.dataaccess.finalactivityMPS2627.service.PassengerService;
import com.dataaccess.finalactivityMPS2627.service.TicketsService;

import java.util.logging.Logger;

public class Main {

    private static final String HELP_SHORT = "-h";
    private static final String HELP_LONG = "--help";
    private static final String ADD_SHORT = "-a";
    private static final String ADD_LONG= "--add";
    private static final String FLIGHTS_SHORT = "-f";
    private static final String FLIGHTS_LONG = "--flights";
    private static final String BUY_SHORT = "-b";
    private static final String BUY_LONG = "--buy";
    private static final String ORIGINS_SHORT = "-o";
    private static final String ORIGINS_LONG = "--origins";
    private static final String DESTINATION_SHORT = "-d";
    private static final String DESTINATION_LONG = "--destinations";

    public static void main(String[] args) {

        if (args.length == 0) {
            showHelp();
            return;
        }

        String option = args[0];

        switch (option) {
            case HELP_SHORT:
            case HELP_LONG:
                showHelp();
                break;

            case ADD_SHORT:
            case ADD_LONG:
                if (args.length != 2) {
                    System.out.println("Error: You must provide an XML file.\n");
                    LoggerService.log("Error: You must provide an XML file.");
                    showHelp();
                } else {
                    PassengerService.addPassenger(args[1]);
                }
                break;
            case FLIGHTS_SHORT:
            case FLIGHTS_LONG:
                if (args.length != 4) {
                    System.out.println("Error: You must provide all information: flight origin, destination and date. \n");
                    LoggerService.log("Error: You must provide all information: flight origin, destination and date.");
                    showHelp();
                }else{
                    FlightService.searchFlights(args[1], args[2], args[3]);
                }
                break;
            case BUY_SHORT:
            case BUY_LONG:
                if (args.length != 4) {
                    System.out.println("Error: You must provide all information: customer id, flight id and date. \n");
                    LoggerService.log("Error: You must provide all information: customer id, flight id and date.");
                    showHelp();
                }else {
                    TicketsService.buyTickets(args[1], args[2], args[3]);
                }
                break;
            case ORIGINS_SHORT:
            case ORIGINS_LONG:
                if(args.length > 1) {
                    System.out.println("Error: We do not expect any parameter written \n");
                    LoggerService.log("Error: We do not expect any parameter written.");
                    showHelp();
                }else{
                    FlightService.searchOrigins();
                }
                break;
            case DESTINATION_SHORT:
            case DESTINATION_LONG:
                if(args.length != 2) {
                    System.out.println("Error: you need to write the origin \n");
                    LoggerService.log("Error: you need to write the destination");
                    showHelp();
                }else{
                    FlightService.showDestinations(args[1]);
                }
                break;
            default:
                System.out.println("Error: Unknown option \n");
                LoggerService.log("Error: Unknown option.");
                showHelp();
                break;
        }
    }
    public static void showHelp() {
        System.out.println("Usage: flights [options]");
        System.out.println("Options:");
        System.out.println("-h --help: show this help");
        System.out.println("-a, --add <filename.xml>: add the passengers in the XML file to the database.");
        System.out.println("-f, --flights <origin> <destination> <date>: show the flights available from origin to destination");
        System.out.println("-b, --buy <customerId> <flightId> <date>: buy a ticket for a flight in a specific date");
        System.out.println("-o, --origins show the origins of the flights");
        System.out.println("-d, --destinations <origin>: show the destinations of the flights from origin");
    }

}