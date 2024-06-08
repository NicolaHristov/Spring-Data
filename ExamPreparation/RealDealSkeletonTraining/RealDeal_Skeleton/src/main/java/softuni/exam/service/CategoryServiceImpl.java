package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import softuni.exam.models.Categories;
import softuni.exam.models.dto.CategorySeedDto;
import softuni.exam.repository.CategoryRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component

public class CategoryServiceImpl implements CategoryService {


    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
    }


    @Override
    public void seedCategories(List<CategorySeedDto> categories) {

           categories.stream().filter(validationUtil::isValid).
                   map(categorySeedDto -> modelMapper.map(categorySeedDto, Categories.class))
                   .forEach(categoryRepository::save);

    }

    @Override
    public long getEntityCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Categories> getRandomCategories() {
        Set<Categories> categories = new HashSet<>();

        long countRepo = categoryRepository.count();

        for (int i = 0; i < 2; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1,countRepo + 1);
            categories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }
}
