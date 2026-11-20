package com.dataaccess.finalactivityMPS2627.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidationClass {

    private static final String IATA_REGEX = "^[A-Za-z]{3}$";
    private static final String FLIGHT_CODE_REGEX = "^[A-Za-z0-9]{2,10}$";
    private static final String CUSTOMER_ID_REGEX = "^[A-Za-z0-9]{1,10}$";

    public static boolean validIata(String iata) {
        return iata != null && iata.trim().matches(IATA_REGEX);
    }

    public static boolean validFlightCode(String flightCode) {
        return flightCode != null && flightCode.trim().matches(FLIGHT_CODE_REGEX);
    }

    public static boolean validCustomerId(String customerId) {
        return customerId != null && customerId.trim().matches(CUSTOMER_ID_REGEX);
    }

    public static LocalDate correctDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean pastDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
