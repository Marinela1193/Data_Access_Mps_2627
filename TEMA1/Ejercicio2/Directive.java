package TEMA1.Ejercicio2;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Directive extends Employee {
    
    private String _position;
    private Set<Employee> _supervises = new HashSet<Employee>();

    public Directive(String name, LocalDate birthDate, double salary, String position) {
        super(name, birthDate, salary);
        this._position = position;
    }

    public String get_position() {
        return _position;
    }

    public void set_position(String _position) {
        this._position = _position;
    }

    public int subordinates () {
        return _supervises.size();
    }
    
}
