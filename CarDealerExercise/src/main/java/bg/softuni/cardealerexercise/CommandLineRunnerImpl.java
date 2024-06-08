package bg.softuni.cardealerexercise;

import bg.softuni.cardealerexercise.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomService customService;
    private final SaleService saleService;

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomService customService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customService = customService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
       supplierService.seedSuppliers();
       partService.seedParts();
       carService.seedCars();
       customService.seedCustomers();
       saleService.seedSales();

//       customService.exportOrderedCustomers();
//       carService.exportCarsFromToyota();
//       supplierService.extractLocalSupplier();
//       carService.exportCarAndParts();
//       customService.exportCustomersWithBoughtCars();
       saleService.exportSalesWithDiscount();
    }
}
