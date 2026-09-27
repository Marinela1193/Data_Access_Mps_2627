package TEMA1.Ejercicio2;

import java.time.LocalDate;


    public class Person {
    
    private String _name;
    private LocalDate _birthDate;

    public Person(String name, LocalDate birthDate) {
        this._name = name;
        this._birthDate = birthDate;
    }

    public String get_name() {
        return _name;
    }

    public LocalDate get_birthDate() {
        return _birthDate;
    }   

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_birthDate(LocalDate birthDate) {

    if (birthDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException(
            ErrorMessages.DATE_OF_BIRTH_ERROR
        );
    }

    this._birthDate = birthDate;
    }

    public int age() {
        return (LocalDate.now().getYear() - _birthDate.getYear());
    }

}
