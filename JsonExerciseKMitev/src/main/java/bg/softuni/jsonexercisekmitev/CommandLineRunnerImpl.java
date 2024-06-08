package bg.softuni.jsonexercisekmitev;

import bg.softuni.jsonexercisekmitev.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
         categoryService.seedCategories();
         userService.seedUsers();
         productService.seedUsers();

//         productService.printAllProductsInRange(BigDecimal.valueOf(500L),BigDecimal.valueOf(1000L));
//        userService.printAllUsersAndSoldProducts();
//        categoryService.printAllCategoriesByProduct();
//        userService.printAllUsersAndSoldProducts();
//        userService.printGetUserAndProduct();

        supplierService.seedSuppliers();
        partService.seedParts();
//        carService.seedCars();
        customerService.seedCustomers();
        saleService.seedSales();

        customerService.exportOrderedCustomers();
        carService.exportCarsFromToyota();
        supplierService.exportSupplier();
        carService.exportCarsWithParts();
        customerService.exportCustomersWithBoughtCars();
        saleService.exportSalesWithDiscount();
    }
}
