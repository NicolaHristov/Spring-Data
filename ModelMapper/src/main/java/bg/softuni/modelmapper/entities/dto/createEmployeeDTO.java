package bg.softuni.modelmapper.entities.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class createEmployeeDTO {


    private String firstName;
    private  String lastName;
    private  BigDecimal salary;
    private  LocalDate birthday;
    private AddressDTO address;



    public createEmployeeDTO(String firstName, String lastName, BigDecimal salary, LocalDate birthday, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public AddressDTO getAddress() {
        return address;
    }
}
