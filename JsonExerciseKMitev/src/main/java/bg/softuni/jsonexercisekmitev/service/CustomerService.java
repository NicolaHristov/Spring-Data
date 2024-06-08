package bg.softuni.jsonexercisekmitev.service;

import java.io.IOException;

public interface CustomerService {

    void seedCustomers() throws IOException;

    void exportOrderedCustomers() ;


    void exportCustomersWithBoughtCars() throws IOException;
}
