package TEMA2.Ejercicio7;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ContactSAXHandler extends DefaultHandler {
    protected String tagContent;

    private ContactObj contact;

    private List<ContactObj> contacts = new ArrayList<>();

    private String cellPhone;
    private String homePhone;
    private String workPhone;

    private boolean insideEmails = false;
    private boolean insidePhones = false;

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        if (qName.equals("contact")) {

            contact = new ContactObj();

            cellPhone = null;
            workPhone = null;
            homePhone = null;

        }

        if (qName.equals("emails")) {
            insideEmails = true;
        }

        if (qName.equals("phones")) {
            insidePhones = true;
        }
    }

    // Tag content
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        tagContent = new String(ch, start, length);
    }

    // Tag ending
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (!qName.isBlank()) {
            if (qName.equals("name")) {
                contact.setName(tagContent);

            } else if (qName.equals("surname")) {
                contact.setSurname(tagContent);

            } else if (qName.equals("cell") && insidePhones) {
                cellPhone = tagContent;

            } else if (qName.equals("work") && insidePhones) {
                workPhone = tagContent;

            } else if (qName.equals("home") && insidePhones) {
                homePhone = tagContent;

            } else if (qName.equals("work") && insideEmails) {
                contact.setEmail(tagContent);

            } else if (qName.equals("home") && insideEmails) {
                contact.setEmail(tagContent);

            } else if (qName.equals("emails")) {
                insideEmails = false;

            } else if (qName.equals("phones")) {
                insidePhones = false;

            } else if (qName.equals("contact")) {

                if (cellPhone != null) {
                    contact.setPhoneNumber(cellPhone);
                } else if (workPhone != null) {
                    contact.setPhoneNumber(workPhone);
                } else if (homePhone != null) {
                    contact.setPhoneNumber(homePhone);
                }
                contacts.add(contact);
            }
        }
    }
    public List<ContactObj> getContacts() {
        return contacts;
    }
}
