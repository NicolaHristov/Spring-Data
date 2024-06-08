package JSONProcessingEx.service;

import JSONProcessingEx.model.entity.Categories;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Categories> findRandomCategories();
}
