package TEMA2.Ejercicio7;

import java.io.*;
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
        System.out.println("Choose an option:");
        System.out.println("1. Add contact");
        System.out.println("2. Search contact by full name");
        System.out.println("3. Search contact by phone");

        int option = scanner.nextInt();
        scanner.nextLine();

        //añadir contacto
        if (option == 1) {
            ContactObj newContact = createContact(scanner);
            contactAgenda.addContact(newContact);

        } else if (option == 2) {
            System.out.print("Enter the name: ");
            String searchName = scanner.nextLine();

            System.out.print("Enter the surname: ");
            String searchSurname = scanner.nextLine();

            if (!searchName.isBlank() && !searchSurname.isBlank()) {
                ContactObj contact = contactAgenda.searchContactByFullName(searchName, searchSurname);
                if (contact != null) {
                    System.out.println("Contact found: " + contact);
                } else {
                    System.out.println("Contact not found.");
                }

            } else {
                System.out.println("Name and surname cannot be empty.");
            }
            //BUSCAR POR TELEFONO METODO
        } else if (option == 3) {
            System.out.print("Enter the phone number to search the contact: ");
            String searchPhoneNumber = scanner.nextLine();

            if (!searchPhoneNumber.isBlank()) {
                ContactObj contact = contactAgenda.searchContactByPhone(searchPhoneNumber);
                if (contact != null) {
                    System.out.println("Contact found: " + contact);
                } else {
                    System.out.println("Contact not found.");
                }

            } else {
                System.out.println("Phone number cannot be empty.");
            }
        }else {
            System.out.println("Invalid option.");
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

        return new ContactObj(
                name,
                surname,
                email,
                phoneNumber,
                description
        );
    }
}


