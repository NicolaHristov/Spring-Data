package com.example.football.service.impl;

import com.example.football.models.dto.TownsRootDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
public class TownServiceImpl implements TownService {

    private static final String TOWN_FILE_PATH = "D:\\Работен плот\\Spring Data Projects\\Player Finder Alone\\skeleton\\src\\main\\resources\\files\\json\\towns.json";

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
//    private final Validator validator;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

//    public TownServiceImpl(TownRepository townRepository) {
//        this.townRepository = townRepository;
//        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
//        this.gson = new GsonBuilder().create();
//        this.modelMapper = new ModelMapper();
//    }




    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }



//    @Override
//    public String importTowns() throws IOException {
//
//        //ПРАВИЛНО МИ СЕ ИМПОРТВАТ В БАЗАТА
//        TownsRootDto[] importTownDto = gson.fromJson(readTownsFileContent(), TownsRootDto[].class);
//
//        StringBuilder stringBuilder = new StringBuilder();
//        List<String> result = new ArrayList<>();
//        for (TownsRootDto townDto : importTownDto) {
////            boolean isValidTown = this.townRepository.findByName(townDto.getName());
//            Optional<Town> optTow = this.townRepository.findByName(townDto.getName());
//
//
//            boolean isValid = validationUtil.isValid(townDto) && optTow.isEmpty();
//
//            stringBuilder.append(isValid ?  String.format("Successfully imported town %s - %d",townDto.getName(),townDto.getPopulation())
//                 : "Invalid town").append(System.lineSeparator());
//
//            if(isValid){
//                Town town = modelMapper.map(townDto,Town.class);
//                this.townRepository.save(town);
//            }
//        }
//
//        return stringBuilder.toString();
//    }

//    @Override
//    public String importTowns() throws IOException {
//        TownsRootDto[] importTownDto = gson.fromJson(readTownsFileContent(), TownsRootDto[].class);
    //ПРАВИЛНО МИ СЕ ИМПОРТВАТ В БАЗАТА
//
//
//        List<String> result = new ArrayList<>();
//        for (TownsRootDto townDto : importTownDto) {
//            Set<ConstraintViolation<TownsRootDto>> validationErrors =
//                    validator.validate(townDto);
//
//            if(validationErrors.isEmpty()){
//                Optional<Town> optTow = this.townRepository.findByName(townDto.getName());
//
//                if(optTow.isEmpty()){
//                    Town town = modelMapper.map(townDto, Town.class);
//                    this.townRepository.save(town);
//
//                    String message = String.format("Successfully imported town %s - %d",town.getName(),town.getPopulation());
//                    result.add(message);
//                }else{
//                    result.add("Invalid town");
//                }
//
//            }else{
//                result.add("Invalid town");
//            }
//
//        }
//        return String.join("\n",result);
//    }


//    @Override
//    public String importTowns() throws IOException {
//     StringBuilder stringBuilder = new StringBuilder();
//        Arrays.stream(gson.fromJson(readTownsFileContent(), TownsRootDto[].class))
//                .filter(townsRootDto -> {
//                    Optional<Town>iSTownExist = townRepository.findByName(townsRootDto.getName());
//                    boolean isValid = validationUtil.isValid(townsRootDto) && iSTownExist.isEmpty();// && !isTownExist(townsRootDto.getName())
//
//
//                 stringBuilder.append(isValid ? String.format("Successfully import Town %s %d",townsRootDto.getName(),townsRootDto.getPopulation())
//                         : "Invalid town").append(System.lineSeparator());
//
//                  return isValid;
//                })
//                .map(townsRootDto -> modelMapper.map(townsRootDto, Town.class)).forEach(townRepository::save);
//
//        return stringBuilder.toString();
//    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTownsFileContent(), TownsRootDto[].class))
                .filter(townsRootDto -> {
                    boolean isTownExist = townRepository.existsByName(townsRootDto.getName());
                    boolean isValid = validationUtil.isValid(townsRootDto) && !isTownExist;

                    stringBuilder.append(isValid ? String.format("Successfully import Town %s %d"
                            ,townsRootDto.getName(),townsRootDto.getPopulation())
                            : "Invalid town").append(System.lineSeparator());

                   return isValid;
                }).map(townsRootDto -> modelMapper.map(townsRootDto,Town.class))
                .forEach(townRepository::save);


        return stringBuilder.toString();
    }


    private boolean isTownExist(String name) {
        return townRepository.existsByName(name);
    }
}
