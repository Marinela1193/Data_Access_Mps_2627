package TEMA1.Ejercicio2;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Client extends Person {
    
    private String _phoneNumber;
    private Set<Company> _isClientOf = new HashSet<Company>();

    public Client(String name, LocalDate birthDate,String phoneNumber) {
        super(name, birthDate);
        this._phoneNumber = phoneNumber;
    }


    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        String phoneNumberPattern = "^[0-9]{9}$";
        if (_phoneNumber.matches(phoneNumberPattern)) {
            this._phoneNumber = _phoneNumber;
        } else {
            throw new IllegalArgumentException(ErrorMessages.PHONE_NUMBER_ERROR);
        }
        
    }
    
}
