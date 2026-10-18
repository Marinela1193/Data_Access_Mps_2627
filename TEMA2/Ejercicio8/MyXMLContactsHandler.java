class myXMLContactsHandler extends DefaultHandler {
    protected String tagContent;
    // Tag opening found
//
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        if ( qName.equals("contact") ) {
            System.out.println( "ID: " + attributes.getValue("id"));
        }
    }
    // Tag content, usually CDATA
//
    public void characters( char ch[], int start, int length )
            throws SAXException {
        tagContent = new String( ch, start, length );
    }
    // Tag ending
//
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ( !qName.isBlank() ) {
            if ( !qName.equals( "contact" ) ) {
                System.out.println(" " + qName + ": " + tagContent);
            }
        }
    }
}
    }