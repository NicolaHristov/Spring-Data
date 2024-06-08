package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.awt.font.TextHitInfo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TownServiceImpl implements TownService {


    private static final String TOWNS_PATH_RESOURCES = "D:\\Работен плот\\Spring Data Projects\\Regular Exam - 1 August 2021\\skeleton\\skeleton\\src\\main\\resources\\files\\json\\towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().create();
        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return townRepository.count() > 0 ;
    }

    @Override
    public String readTownsFileContent() throws IOException {
//        Path path = Path.of("src","main","resources","files","json","towns.json");
//        Path path1 = Path.of("src", "main", "resources", "files", "json", "towns.json");
//
////
//        return String.join("\n",Files.readAllLines(path));
        return Files.readString(Path.of(TOWNS_PATH_RESOURCES));

    }

    @Override
    public String importTowns() throws IOException {
        ImportTownDto[] importTownDto = gson.fromJson(readTownsFileContent(), ImportTownDto[].class);


        List<String> result = new ArrayList<>();
        for (ImportTownDto townDto : importTownDto) {
            Set<ConstraintViolation<ImportTownDto>> validationErrors =
                    validator.validate(townDto);

            if(validationErrors.isEmpty()){
                Optional<Town>optTow = this.townRepository.findByName(townDto.getName());

                if(optTow.isEmpty()){
                    Town town = modelMapper.map(townDto, Town.class);
                    this.townRepository.save(town);

                    String message = String.format("Successfully imported town %s - %d",town.getName(),town.getPopulation());
                    result.add(message);
                }else{
                   result.add("Invalid town");
                }

            }else{
                result.add("Invalid town");
            }

        }
        return String.join("\n",result);
    }
}
