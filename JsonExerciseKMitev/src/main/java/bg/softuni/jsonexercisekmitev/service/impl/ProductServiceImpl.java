package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.Category;
import bg.softuni.jsonexercisekmitev.data.entity.Product;
import bg.softuni.jsonexercisekmitev.data.entity.User;
import bg.softuni.jsonexercisekmitev.data.repository.CategoryRepository;
import bg.softuni.jsonexercisekmitev.data.repository.ProductRepository;
import bg.softuni.jsonexercisekmitev.data.repository.UserRepository;
import bg.softuni.jsonexercisekmitev.service.ProductService;
import bg.softuni.jsonexercisekmitev.service.dtos.ProductInRangeDto;
import bg.softuni.jsonexercisekmitev.service.dtos.ProductSeedDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    private static final String FILE_PATH = "src/main/resources/files/products.json";
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        if(productRepository.count()>0){
            return;
        }
        ProductSeedDto[]productSeedDtos = gson.fromJson(new FileReader(FILE_PATH),ProductSeedDto[].class);

        for (ProductSeedDto productSeedDto : productSeedDtos) {
            if(!validationUtil.isValid(productSeedDto)){
                validationUtil.getViolations(productSeedDto).forEach(vio -> System.out.println(vio.getMessage()));
                continue;
            }

            Product product = modelMapper.map(productSeedDto,Product.class);
            product.setBuyer(getRandomUser(true));
            product.setSeller(getRandomUser(false));
            product.setCategories(getRandomCategories());

            productRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<ProductInRangeDto> getAllProductsInRange(BigDecimal lower, BigDecimal upper) {
//        List<ProductInRangeDto> collect = productRepository.findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L)).stream().
//                filter(validationUtil::isValid).map(product -> modelMapper.map(product, ProductInRangeDto.class)).collect(Collectors.toList());
      return  productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(lower, upper).stream()
                    .map(product -> {
                    ProductInRangeDto map = modelMapper.map(product, ProductInRangeDto.class);
                    map.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());

                    return map;
                }).sorted(Comparator.comparing(ProductInRangeDto::getPrice))
              .collect(Collectors.toList());

//        return productInRangeDtos;

    }

    @Override
    public void printAllProductsInRange(BigDecimal from, BigDecimal to) {
        System.out.println(gson.toJson(getAllProductsInRange(from, to)));
    }

    private User getRandomUser(boolean isBuyer) {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count()+1);

        return isBuyer && randomId % 4 == 0 ? null : userRepository.findById(randomId).get();
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int randomCount = ThreadLocalRandom.current().nextInt(1,4);

        for (int i = 0; i < randomCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1,categoryRepository.count()+1);
            categories.add(categoryRepository.findById(randomId).get());
        }
        return categories;
    }


}
