package TEMA2.Ejercicio7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* The program must permit to create new contacts,
show the current list of contacts and search for a contact by its full name
or phone. Data must be stored and retrieved in a file named contacts.obj.
If it doesn’t exist, it must be created. */

public class ContactAgenda implements Serializable {

    List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {

        Contact existingContact = searchContact(contact.getName(), contact.getSurname(), contact.getPhoneNumber());
        if (existingContact != null) {
            System.out.println("Contact with the same name and surname already exists.");
            return;
        } else {
            contacts.add(contact);
            System.out.println("Contact added successfully.");
        }
    }

    Contact searchContact(String name, String surname, String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name) && contact.getSurname().equals(surname)) {
                return contact;
            } else if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        return null;
    }

    public void showContacts() {

        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

}
