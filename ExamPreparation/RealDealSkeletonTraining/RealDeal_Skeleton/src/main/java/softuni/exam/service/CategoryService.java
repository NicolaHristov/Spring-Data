package softuni.exam.service;

//import JSONProcessingEx.model.entity.Categories;



import softuni.exam.models.Categories;
import softuni.exam.models.dto.CategorySeedDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
//    void seedCategories() throws IOException;


    void seedCategories(List<CategorySeedDto> categories);

    long getEntityCount();
    Set<Categories> getRandomCategories();
}
