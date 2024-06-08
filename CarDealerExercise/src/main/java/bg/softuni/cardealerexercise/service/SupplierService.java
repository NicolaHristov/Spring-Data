package bg.softuni.cardealerexercise.service;

import jakarta.xml.bind.JAXBException;

public interface SupplierService {


    void seedSuppliers() throws JAXBException;

    void extractLocalSupplier() throws JAXBException;
}
