package TEMA1.Ejercicio2;

import java.time.LocalDate;

public class Employee extends Person {

    private double _salary;

    public Employee(String name, LocalDate birthDate, double salary) {
        super(name, birthDate);
        this._salary = salary;
    }

    public double get_salary() {
        return _salary;
    }

    public void set_salary(double _salary) {
       if (_salary < 0) {
            throw new IllegalArgumentException(ErrorMessages.SALARY_ERROR);
        }
        this._salary = _salary;
    }

}
