package TEMA2.Ejercicio7;

import java.io.Serializable;

public class ContactObj implements Serializable {
    protected String name;
    protected String surname;
    protected String email;
    protected String phoneNumber;
    protected String description;

    public ContactObj(String name, String surname, String email, String phoneNumber, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public ContactObj() {}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "Name: " + name + ", Surname: " + surname + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Description: " + description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}



