package bg.softuni.jsonexercisekmitev.service;

import java.io.IOException;

public interface SaleService {

    void seedSales();

    void exportSalesWithDiscount() throws IOException;
}
