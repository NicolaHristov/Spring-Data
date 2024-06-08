package bg.softuni.jsonexercise2.service;

import bg.softuni.jsonexercise2.constants.GlobalConstant;
import bg.softuni.jsonexercise2.model.dto.CategorySeedDto;
import bg.softuni.jsonexercise2.model.entity.Category;
import bg.softuni.jsonexercise2.repository.CategoryRepository;
import bg.softuni.jsonexercise2.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_NAME = "categories.json";
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public void seedCategories() throws IOException {
        if(categoryRepository.count() > 0){
            return;
        }
        String fileContent = Files.readString(Path.of(GlobalConstant.RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[]categorySeedDtos = gson.fromJson(fileContent,CategorySeedDto[].class);

                Arrays.stream(categorySeedDtos).filter(validationUtil::isValid).
                map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);

    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int count = ThreadLocalRandom.current().nextInt(1,3);
        long countRepo = categoryRepository.count();
        for (int i = 0; i <count ; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1,countRepo+1);
            categories.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categories;
    }
}
