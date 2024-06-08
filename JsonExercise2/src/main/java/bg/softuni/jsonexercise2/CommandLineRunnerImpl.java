package bg.softuni.jsonexercise2;

import bg.softuni.jsonexercise2.model.dto.ProductNameAndPriceDto;
import bg.softuni.jsonexercise2.model.dto.UserSoldDto;
import bg.softuni.jsonexercise2.service.CategoryService;
import bg.softuni.jsonexercise2.service.ProductService;
import bg.softuni.jsonexercise2.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_PATH = "src/main/resources/files/out/";
    private static final String PRODUCTS_IN_RANGE = "products-in-range.json";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
         seedData();

        System.out.println("Select exercise:");
        int exNum = Integer.parseInt(bufferedReader.readLine());
        switch (exNum){
            case 1 -> productsInRange();
            case 2 -> soldProducts();
        }
    }

    private void soldProducts() {
      List<UserSoldDto> userSoldDtos = userService.findAllUsersWithMoreThanOneSoldProduct();
    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDto> productDtos =
                productService.findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L),BigDecimal.valueOf(1000L));

        String content = gson.toJson(productDtos);

        writeToFile(OUTPUT_PATH+PRODUCTS_IN_RANGE,content);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
