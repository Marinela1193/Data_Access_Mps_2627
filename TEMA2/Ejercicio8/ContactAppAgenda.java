package TEMA2.Ejercicio7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ContactAppAgenda {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ContactObjAgenda contactAgenda;
        File file = new File("contacts.obj");

        //cargamos agenda
        if (file.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("contacts.obj"))) {

                contactAgenda = (ContactObjAgenda) ois.readObject();

                //si la agenda no está creada la creamos
            } catch (ClassNotFoundException e) {

                System.out.println("Class not found: " + e.getMessage());
                contactAgenda = new ContactObjAgenda();
            }

        } else {

            contactAgenda = new ContactObjAgenda();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Would you like to add a new contact? (yes/no): ");
        String response = scanner.nextLine();

        //añadir contacto
        if (response.equalsIgnoreCase("yes")) {
            ContactObj newContact = createContact(scanner);
            contactAgenda.addContact(newContact);
        } else if (response.equalsIgnoreCase("no")) {
            System.out.println("Would you like to search for a contact? (yes/no): ");

            String searchResponse = scanner.nextLine();

            if (searchResponse.equalsIgnoreCase("yes")) {
                System.out.print("Enter the name: ");
                String searchName = scanner.nextLine();
                System.out.print("Enter the surname: ");
                String searchSurname = scanner.nextLine();
                //BUSCAR OPR NOMBRE METODO
                System.out.print("Enter the phone number: ");
                String searchPhoneNumber = scanner.nextLine();
                //BUSCAR POR TELEFONO METODO
                ContactObj contact = contactAgenda.searchContact(
                        searchName,
                        searchSurname,
                        searchPhoneNumber);

                if (contact != null) {
                    System.out.println("Contact found: " + contact);
                } else {
                    System.out.println("Contact not found.");
                }
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(file))) {

                oos.writeObject(contactAgenda);

            } catch (IOException e) {

                System.out.println(
                        "An error occurred while writing to the file: "
                                + e.getMessage());
            }

            scanner.close();
        }

    }
    private static ContactObj createContact(Scanner scanner) {

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

        ContactObj newContact =
                new ContactObj(
                        name,
                        surname,
                        email,
                        phoneNumber,
                        description
                );
        return newContact;
    }
}


