package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.Category;
import bg.softuni.jsonexercisekmitev.data.entity.Product;
import bg.softuni.jsonexercisekmitev.data.repository.CategoryRepository;
import bg.softuni.jsonexercisekmitev.service.CategoryService;
import bg.softuni.jsonexercisekmitev.service.dtos.CategoryByProductsDto;
import bg.softuni.jsonexercisekmitev.service.dtos.CategorySeedDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String FILE_PATH = "src/main/resources/files/categories.json";

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if(categoryRepository.count()>0){
            return;
        }
          String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));

        CategorySeedDto[]categorySeedDtos = gson.fromJson(jsonContent,CategorySeedDto[].class);
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if(!validationUtil.isValid(categorySeedDto)){
                validationUtil.getViolations(categorySeedDto).forEach(vio -> System.out.println(vio.getMessage()));
                continue;
            }
            Category category = modelMapper.map(categorySeedDto,Category.class);
            categoryRepository.saveAndFlush(category);
        }
    }

//    @Override
//    public List<CategoryByProductsDto> getAllCategoriesByProduct() {
//       return categoryRepository.findAllCategoriesByProducts().stream().map(category -> {
//            CategoryByProductsDto dto = modelMapper.map(category, CategoryByProductsDto.class);
//
//            dto.setProductsCount(category.getProducts().size());
//            BigDecimal averageSum = category.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get();
//            BigDecimal sum = category.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get();
//            dto.setAveragePrice(averageSum);
//            dto.setTotalRevenue(averageSum.divide(BigDecimal.valueOf(category.getProducts().size()),MathContext.DECIMAL128));
//             return dto;
//
//        }).collect(Collectors.toList());
//    }

    @Override
    public List<CategoryByProductsDto> getAllCategoriesByProduct() {
        return categoryRepository.findAllCategoriesByProducts().stream().map(category -> {
            CategoryByProductsDto categoryByProductsDto = modelMapper.map(category,CategoryByProductsDto.class);

            categoryByProductsDto.setProductsCount(category.getProducts().size());
            BigDecimal sum = category.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get();
            categoryByProductsDto.setTotalRevenue(sum);
            categoryByProductsDto.setAveragePrice(sum.divide(BigDecimal.valueOf(category.getProducts().size()), MathContext.DECIMAL64));

            return categoryByProductsDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void printAllCategoriesByProduct() {
        String json = gson.toJson(getAllCategoriesByProduct());
        System.out.println(json);
    }
}
