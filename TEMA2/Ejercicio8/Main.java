public class Main {
    public static void main( String[] args ) {
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();
            saxParser.parse("contacts.xml", new
                    myXMLContactsHandler());
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}