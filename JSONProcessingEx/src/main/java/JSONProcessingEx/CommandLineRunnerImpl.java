package JSONProcessingEx;

import JSONProcessingEx.model.dto.ProductNameAndPriceDto;
import JSONProcessingEx.service.CategoryService;
import JSONProcessingEx.service.ProductService;
import JSONProcessingEx.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
         seedData();
        System.out.println("Enter exercise:");
        int exNum=Integer.parseInt(bufferedReader.readLine());

        switch (exNum){
            case 1 -> productsInRange();
        }
    }

    private void productsInRange() {
        List<ProductNameAndPriceDto> productDtos =
                productService.findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L),BigDecimal.valueOf(1000L));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
