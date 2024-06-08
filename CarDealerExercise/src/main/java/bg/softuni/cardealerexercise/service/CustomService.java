package bg.softuni.cardealerexercise.service;

import jakarta.xml.bind.JAXBException;

public interface CustomService {


     void seedCustomers() throws JAXBException;

     void exportOrderedCustomers() throws JAXBException;

     void exportCustomersWithBoughtCars() throws JAXBException;
}
