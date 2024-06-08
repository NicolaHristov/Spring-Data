package bg.softuni.jsonexercise2.service;

import bg.softuni.jsonexercise2.constants.GlobalConstant;
import bg.softuni.jsonexercise2.model.dto.ProductNameAndPriceDto;
import bg.softuni.jsonexercise2.model.dto.ProductSeedDto;
import bg.softuni.jsonexercise2.model.entity.Products;
import bg.softuni.jsonexercise2.repository.ProductRepository;
import bg.softuni.jsonexercise2.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_FILE_NAME = "products.json";
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        if(productRepository.count()>0){
            return;
        }

           String fileContent = Files.readString(Path.of(GlobalConstant.RESOURCES_FILE_PATH+PRODUCTS_FILE_NAME));

           ProductSeedDto[]productSeedDtos = gson.fromJson(fileContent,ProductSeedDto[].class);

                Arrays.stream(gson.fromJson(fileContent,ProductSeedDto[].class))
                .filter(validationUtil::isValid).map(productSeedDto -> {
                    Products products = modelMapper.map(productSeedDto, Products.class);
                    products.setSeller(userService.findRandomUser());
                    if(products.getPrice().compareTo(BigDecimal.valueOf(900L))>0 ){
                        products.setBuyer(userService.findRandomUser());
                    }

                    products.setCategories(categoryService.findRandomCategories());

                    return products;
                        })
                .forEach(productRepository::save);
    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
        return productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower,upper).stream()
                .map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto = modelMapper.map(product,ProductNameAndPriceDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));
                    return productNameAndPriceDto;
                }).collect(Collectors.toList());
    }
}
