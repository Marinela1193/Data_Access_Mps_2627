package com.dataaccess.finalactivityMPS2627.util;


import com.dataaccess.finalactivityMPS2627.model.Passenger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class PassengerHandler extends DefaultHandler {

    //we declare the list of passengers, a new passenter and the elementValue
    private List<Passenger> passengers = new ArrayList<>();
    private Passenger currentPassenger = null;
    private StringBuilder elementValue = new StringBuilder();

    //we declare a method to retrieve the passengers from the list
    public List<Passenger> getPassengers () {
        return passengers;
    }

    //method that will make sure that the first tag of our xml is passenger.
    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        elementValue.setLength(0);

        //if the name of the tag equals passenger then we save this passenger into the currentPassenger variable created
        if (qName.equalsIgnoreCase("passenger")) {
            currentPassenger = new Passenger();

            //we make sure the passport is not written as an attribute within the passenger tag
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

    //we focus now on the final tag
    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {

        //if the variable currentPassenger is not empty then we declare a new variable that will save the content
        //of the tags
        if (currentPassenger != null) {
            String content = elementValue.toString().trim();

            //we save the information of the tags within this currentPassenger
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
                //if the final tag equals passenger it means the information of that passenger has finished
            }else if (qName.equalsIgnoreCase("passenger")) {
                //we add the passenger
                passengers.add(currentPassenger);
                //we declare it again as null so we can save another passenger with different information
                currentPassenger = null;
            }
        }
    }
}
