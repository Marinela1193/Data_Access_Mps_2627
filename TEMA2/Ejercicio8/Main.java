package TEMA2.Ejercicio7;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {
    public static void main( String[] args ) {
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();
            saxParser.parse("contacts.xml", new
                    ContactSAXHandler());
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}