package JSONProcessingEx.service.impl;

import JSONProcessingEx.constant.GlobalConstant;
import JSONProcessingEx.model.dto.CategorySeedDto;
import JSONProcessingEx.model.entity.Categories;
import JSONProcessingEx.repository.CategoryRepository;
import JSONProcessingEx.service.CategoryService;
import JSONProcessingEx.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static JSONProcessingEx.constant.GlobalConstant.RESOURCE_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_NAME = "categories.json";
    private final Gson gson;

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(Gson gson, CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gson = gson;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if(categoryRepository.count() > 0){
            return;
        }
        //Какво ни трявба за seedCategories? 1. Трябва да си прочетем файловете-> Ще се нуждаем от пътя до този файл
        String fileContnent = Files.readString(Path.of(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[]categorySeedDtos = gson.fromJson(fileContnent,CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos).filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Categories.class)).forEach(categoryRepository::save);
        //Всяко едно categorySeedDto което е валидно искам МоделМапера да ми го мапне от categorySeedDto към Сategories.class

    }

    @Override
    public Set<Categories> findRandomCategories() {
        Set<Categories> categoriesSet = new HashSet<>();
        int catCount = ThreadLocalRandom.current().nextInt(1,3);
        long totalCategoriesCount = categoryRepository.count();

        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1,totalCategoriesCount + 1);
            // long randomId = ThreadLocalRandom.current().nextLong(1,categoryRepository.count() + 1);
            categoriesSet.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categoriesSet;
    }
}
