package com.dataaccess.finalactivityMPS2627;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            showHelp();
            return;
        }

        String option = args[0];

        switch (option) {
            case "-h":
            case "--help":
                showHelp();
                break;

            case "-a":
            case "--add":
                if (args.length < 2) {
                    System.out.println("Error: You must provide an XML file.\n");
                    showHelp();
                } else {
                    PassengerService.addPassenger(args[1]);
                }
                break;
            case "-f":
                case "--flights":
                    if (args.length < 3) {
                        System.out.println("Error: You must provide all information: customer id, flight id and date. \n");
                        showHelp();
                    }else{
                        FlightService.searchFlights(args[3]);
                    }

            default:
                System.out.println("Error: Unknown option \n");
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