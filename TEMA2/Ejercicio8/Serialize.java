package TEMA2.Ejercicio7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Serialize {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ContactAgenda contactAgenda;
        File file = new File("contacts.obj");

        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("contacts.obj"))) {

                contactAgenda = (ContactAgenda) ois.readObject();

            } catch (ClassNotFoundException e) {

                System.out.println("Class not found: " + e.getMessage());
                contactAgenda = new ContactAgenda();
            }

        } else {

            contactAgenda = new ContactAgenda();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Would you like to add a new contact? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter surname: ");
            String surname = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            Contact newContact = new Contact(name, surname, email, phoneNumber, description);
            contactAgenda.addContact(newContact);

        } else if (response.equalsIgnoreCase("no")) {
            System.out.println("Would you like to search for a contact? (yes/no): ");
            String searchResponse = scanner.nextLine();
            if (searchResponse.equalsIgnoreCase("yes")) {
                System.out.print("Enter the name of the contact to search for: ");
                String searchName = scanner.nextLine();
                System.out.print("Enter the surname of the contact to search for: ");
                String searchSurname = scanner.nextLine();
                System.out.print("Enter the phone number of the contact to search for: ");
                String searchPhoneNumber = scanner.nextLine();

                Contact contact = contactAgenda.searchContact(
                        searchName,
                        searchSurname,
                        searchPhoneNumber);

                if (contact != null) {
                    System.out.println("Contact found: " + contact);
                } else {
                    System.out.println("Contact not found.");
                }
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("contacts.obj"))) {

            oos.writeObject(contactAgenda);

        } catch (FileNotFoundException e) {

            System.out.println(
                    "File not found: " + e.getMessage());

        } catch (IOException e) {

            System.out.println(
                    "An error occurred while writing to the file: "
                            + e.getMessage());
        }

        scanner.close();
    }

}
