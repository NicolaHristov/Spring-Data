package JSONProcessingEx.service.impl;

import JSONProcessingEx.constant.GlobalConstant;
import JSONProcessingEx.model.dto.ProductNameAndPriceDto;
import JSONProcessingEx.model.dto.ProductSeedDto;
import JSONProcessingEx.model.dto.UserSeedDto;
import JSONProcessingEx.model.entity.Products;
import JSONProcessingEx.model.entity.User;
import JSONProcessingEx.repository.ProductRepository;
import JSONProcessingEx.service.CategoryService;
import JSONProcessingEx.service.ProductService;
import JSONProcessingEx.service.UserService;
import JSONProcessingEx.util.ValidationUtil;
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

import static JSONProcessingEx.constant.GlobalConstant.RESOURCE_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_FILE_PATH = "products.json";
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
//        if(productRepository.count() > 0){
//            return;
//        }


        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCE_FILE_PATH + PRODUCTS_FILE_PATH)),ProductSeedDto[].class))
                .filter(validationUtil::isValid).map(productSeedDto -> {
                    Products products = modelMapper.map(productSeedDto,Products.class);
                    modelMapper.typeMap(ProductNameAndPriceDto.class, Products.class).addMappings( mapper -> mapper.map(ProductNameAndPriceDto::getSeller,Products::setSeller));
                    products.setSeller(userService.findRandomUser());
                    if(products.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0){
                        products.setBuyer(userService.findRandomUser());
                    }
                    products.setCategories(categoryService.findRandomCategories());

                    return products;
                });
//        // Cannot invoke "JSONProcessingEx.model.entity.User.getFirstName()" because the return value of "JSONProcessingEx.model.entity.Products.getSeller()" is null


//        String fileContent = Files.readString(Path.of(RESOURCE_FILE_PATH + PRODUCTS_FILE_PATH));
//
//        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent,ProductSeedDto[].class);
//
//        Arrays.stream(productSeedDtos)
//                .filter(validationUtil::isValid)
//                .map(productSeedDto -> {
//            Products product = modelMapper.map(productSeedDto,Products.class);
//            product.setSeller(userService.findRandomUser());
//
//
//
//            if(product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0){
//                product.setBuyer(userService.findRandomUser());
//            }
//            product.setCategories(categoryService.findRandomCategories());
//            return product;
//        });
//        System.out.println();

    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
        return productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower,upper)
                .stream().map( products -> {
                    ProductNameAndPriceDto productNameAndPriceDto = modelMapper.map(products,ProductNameAndPriceDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            products.getSeller().getFirstName(),
                            products.getSeller().getLastName()));

                    return productNameAndPriceDto;
                }).collect(Collectors.toList());

    }
}
