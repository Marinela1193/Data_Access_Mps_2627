package TEMA2.Ejercicio7;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class Main {
    public static void main( String[] args ) {
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();

            ContactSAXHandler contactSAXHandler = new ContactSAXHandler();

            saxParser.parse("contacts.xml", contactSAXHandler);

            List<ContactObj> contacts = contactSAXHandler.getContacts();

            for (ContactObj contact : contacts) {
                System.out.println(contact);
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}