package TEMA1.Ejercicio2;

import java.util.HashSet;
import java.util.Set;

public class Company {
    private String _name;
    private Set<Employee> _staff = new HashSet<Employee>();
    private Set<Client> _clients = new HashSet<Client>();

    public Company(String name) {
        this._name = name;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int staffSize() {
        return _staff.size();
    }

    public int clientsSize() {
        return _clients.size();
    }
}