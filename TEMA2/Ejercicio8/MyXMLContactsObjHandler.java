package TEMA2.Ejercicio7;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MyXMLContactsObjHandler extends DefaultHandler {
    protected String tagContent;
    private String name;
    private String surname;
    private String cellPhone;
    private String homePhone;
    private String email;
    private String workPhone;

    private boolean insideEmails = false;
    private boolean insidePhones = false;

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        if (qName.equals("contact")) {
            name = null;
            surname = null;

            cellPhone = null;
            workPhone = null;
            homePhone = null;

            email = null;
        }

        if (qName.equals("emails")) {
            insideEmails = true;
        }

        if (qName.equals("phones")) {
            insidePhones = true;
        }
    }

    // Tag content, usually CDATA
//
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        tagContent = new String(ch, start, length);
    }

    // Tag ending
//
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (!qName.isBlank()) {
            if (qName.equals("name")) {
                name = tagContent;
            } else if (qName.equals("surname")) {
                surname = tagContent;
            } else if (qName.equals("cell") && insidePhones) {
                cellPhone = tagContent;
            } else if (qName.equals("work") && insidePhones) {
                workPhone = tagContent;
            } else if (qName.equals("home") && insidePhones) {
                homePhone = tagContent;
            } else if (qName.equals("work") && insideEmails) {
                email = tagContent;
            } else if (qName.equals("home") && insideEmails) {
                email = tagContent;
            } else if (qName.equals("emails")) {
                insideEmails = false;
            } else if (qName.equals("phones")) {
                insidePhones = false;
            } else if (qName.equals("contact")) {

                String phoneNumber = null;

                if (cellPhone != null) {
                    phoneNumber = cellPhone;
                } else if (workPhone != null) {
                    phoneNumber = workPhone;
                } else if (homePhone != null) {
                    phoneNumber = homePhone;
                }

                System.out.println(
                        name + " " + surname +
                                " - " + phoneNumber
                );
            }

        }
    }
}
