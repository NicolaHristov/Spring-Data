package softuni.exam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.exam.models.dto.CategoriesRootDto;
import softuni.exam.models.dto.ProductViewRootDto;
import softuni.exam.models.dto.ProductsRootDto;
import softuni.exam.models.dto.UsersRootDto;
import softuni.exam.models.dto.users2dto.UserViewRootDto;
import softuni.exam.service.CategoryService;
import softuni.exam.service.ProductService;
import softuni.exam.service.UserService;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CommandLineRuneerImpl implements CommandLineRunner {


    private final XmlParser xmlParser;
    private final CategoryService categoryService;

    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private   static  final String RESOURCES_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String FINAL = "D:\\Работен плот\\RealDealSkeletonTraining\\RealDeal_Skeleton\\src\\main\\resources\\files\\categories.xml";
    private static final String FINAL_USERS = "D:\\Работен плот\\RealDealSkeletonTraining\\RealDeal_Skeleton\\src\\main\\resources\\files\\users.xml";
    private static final String FINAL_PRODUCTS = "D:\\Работен плот\\RealDealSkeletonTraining\\RealDeal_Skeleton\\src\\main\\resources\\files\\products.xml";
    private static final String OUT_PATH_FILE = "D:\\Работен плот\\RealDealSkeletonTraining\\RealDeal_Skeleton\\src\\main\\resources\\files\\out";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "D:\\Работен плот\\RealDealSkeletonTraining\\RealDeal_Skeleton\\src\\main\\resources\\files\\out\\products-in-range.xml";
    private static final String SOLD_FILE_NAME = "D:\\Работен плот\\RealDealSkeletonTraining\\RealDeal_Skeleton\\src\\main\\resources\\files\\out\\sold-products.xml";


    public CommandLineRuneerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Select exercise:");
        int exerciseNumber = Integer.parseInt(bufferedReader.readLine());
        switch (exerciseNumber){
            case 1 : productsInRange();
            case 2 : successfullySold();
        }
    }

    private void successfullySold() throws JAXBException {
        UserViewRootDto rootDto = userService.findSuccessfullySold();

        System.out.println();

        xmlParser.writeToFile(SOLD_FILE_NAME,UsersRootDto.class);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto = productService.findProductsInRangeWihtoutBuyer();

        xmlParser.writeToFile(PRODUCTS_IN_RANGE_FILE_NAME,rootDto);

    }

    private void seedData() throws JAXBException, FileNotFoundException {
        if(categoryService.getEntityCount()==0){
            CategoriesRootDto categoriesRootDto = xmlParser.fromFile(FINAL, CategoriesRootDto.class);
            categoryService.seedCategories(categoriesRootDto.getCategories());
        }

        if(userService.getCount() == 0){
            UsersRootDto usersRootDto = xmlParser.fromFile(FINAL_USERS, UsersRootDto.class);

            userService.seedUsers(usersRootDto.getUsers());
        }

        if(productService.getCount()==0){
            ProductsRootDto productsRootDto = xmlParser.fromFile(FINAL_PRODUCTS, ProductsRootDto.class);

            productService.seedProducts(productsRootDto.getProducts());
            System.out.println();
        }



    }
}
