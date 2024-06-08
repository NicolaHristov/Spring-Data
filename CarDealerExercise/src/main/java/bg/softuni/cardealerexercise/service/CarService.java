package bg.softuni.cardealerexercise.service;

import jakarta.xml.bind.JAXBException;

public interface CarService {

    void seedCars() throws JAXBException;

    void exportCarsFromToyota() throws JAXBException;

    void exportCarAndParts() throws JAXBException;
}
