package com.dataaccess.finalactivityMPS2627;

import com.dataaccess.finalactivityMPS2627.service.FlightService;
import com.dataaccess.finalactivityMPS2627.service.LoggerService;
import com.dataaccess.finalactivityMPS2627.service.PassengerService;
import com.dataaccess.finalactivityMPS2627.service.TicketsService;

import java.util.logging.Logger;

public class Main {

    //declaramos las opciones como variables para facilitar el código.

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
    private static final String CANCELTICKET_SHORT = "-c";
    private static final String CANCELTICKET_LONG = "--cancel";

    public static void main(String[] args) {

        //if we do not receive any parameter we show the help menu
        if (args.length == 0) {
            showHelp();
            return;
        }

        // we declare the parameter we receive as option
        String option = args[0];

        switch (option) {
            case HELP_SHORT:
            case HELP_LONG:
                showHelp();
                break;

            case ADD_SHORT:
            case ADD_LONG:
                // to make sure the parameters we receive are exactly two, as we expect
                if (args.length != 2) {
                    System.out.println("Error: You must provide an XML file.\n");
                    LoggerService.log("Error: You must provide an XML file.");
                    showHelp();
                } else {
                    //we call the method to add the passengers of the xml file
                    PassengerService.addPassenger(args[1]);
                }
                break;
            case FLIGHTS_SHORT:
            case FLIGHTS_LONG:
                //we make sure we receive exactly 4 parameters
                if (args.length != 4) {
                    System.out.println("Error: You must provide all information: flight origin, destination and date. \n");
                    LoggerService.log("Error: You must provide all information: flight origin, destination and date.");
                    showHelp();
                }else{
                    //we call the method searchFlights, and we pass the parameters received
                    FlightService.searchFlights(args[1], args[2], args[3]);
                }
                break;
            case BUY_SHORT:
            case BUY_LONG:
                //we make sure we receive exactly 4 parameters
                if (args.length <4 || args.length > 5) {
                    System.out.println("Error: You must provide all information: customer id, flight id and date. \n");
                    LoggerService.log("Error: You must provide all information: customer id, flight id and date.");
                    showHelp();
                }else if(args.length ==5){

                    TicketsService.buyTicketsAndPrint(args[1], args[2], args[3]);
                }
                else {
                    //we call the method buyTickets, and we pass  the parameters received
                    TicketsService.buyTickets(args[1], args[2], args[3]);
                }
                break;
            case ORIGINS_SHORT:
            case ORIGINS_LONG:
                //we make sure we receive by parameters just the letter -o or --origins
                if(args.length > 1) {
                    System.out.println("Error: We do not expect any parameter written \n");
                    LoggerService.log("Error: We do not expect any parameter written.");
                    showHelp();
                }else{
                    //we call the method searchOrigins
                    FlightService.searchOrigins();
                }
                break;
            case DESTINATION_SHORT:
            case DESTINATION_LONG:
                //we make sure we receive exactly 2 parameters
                if(args.length != 2) {
                    System.out.println("Error: you need to write the origin \n");
                    LoggerService.log("Error: you need to write the destination");
                    showHelp();
                }else{
                    //we call the method showDestinations
                    FlightService.showDestinations(args[1]);
                }
                break;
            case CANCELTICKET_SHORT:
            case CANCELTICKET_LONG:
                if(args.length != 3) {
                    System.out.println("Error: you need to write the ID of the passenger and the date \n");
                    LoggerService.log("Error: you need to write the ID of the passenger and the date");
                    showHelp();
                }else{
                    TicketsService.cancelTicket(args[1], args[2]);
                }
                break;
            default:
                //if we do not receive any of the parameters expected it will lead to an error that we will capture
                //in our log, and we will show the help menu
                System.out.println("Error: Unknown option \n");
                LoggerService.log("Error: Unknown option.");
                showHelp();
                break;
        }
    }
    //method that shows the help menu
    public static void showHelp() {
        System.out.println("Usage: flights [options]");
        System.out.println("Options:");
        System.out.println("-h --help: show this help");
        System.out.println("-a, --add <filename.xml>: add the passengers in the XML file to the database.");
        System.out.println("-f, --flights <origin> <destination> <date>: show the flights available from origin to destination");
        System.out.println("-b, --buy <customerId> <flightId> <date> [--print]: buy a ticket for a flight in a specific date");
        System.out.println("-o, --origins show the origins of the flights");
        System.out.println("-d, --destinations <origin>: show the destinations of the flights from origin");
        System.out.println("-c, --cancel <customerId> <date>: cancel the ticket bought by the passenger");
    }

}