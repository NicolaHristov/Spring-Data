package bg.softuni.jsonexercise2.service;

import bg.softuni.jsonexercise2.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> findRandomCategories();
}
