package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import softuni.exam.models.Products;
import softuni.exam.models.User;
import softuni.exam.models.dto.ProductDto;
import softuni.exam.models.dto.ProductViewRootDto;
import softuni.exam.models.dto.ProductWithSellerDto;
import softuni.exam.repository.ProductRepository;
import softuni.exam.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final CategoryService categoryService;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public ProductServiceImpl(UserService userService, CategoryService categoryService, ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getCount() {
        return productRepository.count();
    }

    @Override
    public void seedProducts(List<ProductDto> products) {
                 products.stream().filter(validationUtil::isValid)
                .map(productDto -> {
                     Products products1 = modelMapper.map(productDto, Products.class);
                     products1.setSeller(userService.getRandomUser());

                     if(products1.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0){
                         products1.setBuyer(userService.getRandomUser());
                     }

                     products1.setCategories(categoryService.getRandomCategories());


                     return products1;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductViewRootDto findProductsInRangeWihtoutBuyer() {
        ProductViewRootDto rootDto = new ProductViewRootDto();

        rootDto.setProducts(productRepository.findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500L),BigDecimal.valueOf(1000L)).
                stream().map(product -> {
                    ProductWithSellerDto productWithSellerDto = modelMapper.map(product, ProductWithSellerDto.class);

                    productWithSellerDto.setSeller(String.format("%s %s",
                    product.getSeller().getFirstName(),product.getSeller().getLastName()));

                    return productWithSellerDto;
                }).collect(Collectors.toList()));

        return rootDto;
    }


}
