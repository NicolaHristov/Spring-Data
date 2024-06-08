package bg.softuni.jsonexercisekmitev.service;

import java.io.IOException;

public interface CarService {

    void seedCars() throws IOException;

    void exportCarsFromToyota() throws IOException;

    void exportCarsWithParts() throws IOException;
}
