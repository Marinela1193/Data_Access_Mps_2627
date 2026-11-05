package com.dataaccess.finalactivityMPS2627;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class PassengerHandler extends DefaultHandler {

    private List<Passenger> passengers = new ArrayList<>();
    private Passenger currentPassenger = null;
    private StringBuilder elementValue = new StringBuilder();

    public List<Passenger> getPassengers () {
        return passengers;
    }

    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        elementValue.setLength(0);

        if (qName.equalsIgnoreCase("passenger") || qName.equalsIgnoreCase("customer")) {
            currentPassenger = new Passenger();

            String attrPassport = attributes.getValue("passportno");
            if (attrPassport != null && !attrPassport.isBlank()) {
                currentPassenger.setPassportNo(attrPassport.trim());
            }
        }
    }

    @Override
    public void characters ( char[] ch, int start, int length) throws SAXException {
        elementValue.append(ch, start, length);
    }

    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
        if (currentPassenger != null) {
            String content = elementValue.toString().trim();

            if(qName.equalsIgnoreCase("passportno")) {
                currentPassenger.setPassportNo(content);
            }else if(qName.equalsIgnoreCase("firstname")) {
                currentPassenger.setFirstName(content);
            }else if(qName.equalsIgnoreCase("lastname")) {
                currentPassenger.setLastName(content);
            }else if(qName.equalsIgnoreCase("address")) {
                currentPassenger.setAddress(content);
            }else if(qName.equalsIgnoreCase("phone")) {
                currentPassenger.setPhone(content);
            }

            passengers.add(currentPassenger);
            currentPassenger = null;
        }
    }
}
