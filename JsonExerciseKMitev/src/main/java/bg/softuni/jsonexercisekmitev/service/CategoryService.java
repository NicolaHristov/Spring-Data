package bg.softuni.jsonexercisekmitev.service;

import bg.softuni.jsonexercisekmitev.service.dtos.CategoryByProductsDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    void seedCategories() throws IOException;


    List<CategoryByProductsDto> getAllCategoriesByProduct();

    void printAllCategoriesByProduct();
}
