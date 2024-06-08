package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Car;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Part;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.CarRepository;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.PartRepository;
import bg.softuni.jsonexercisekmitev.service.CarService;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.*;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String CAR_PATH = "src/main/resources/files/cars.json";
    private static final String EXPORT_CAR_PATH = "src/main/resources/files/export/toyota-cars.json";
    private static final String SECOND_EXPORT_CAR_PATH = "src/main/resources/files/export/toyota-second.json";
    private static final String EXPORT_CAR_PART_CAR_PATH = "src/main/resources/files/export/cars-and-parts.json";
    private static final String SECOND_EXPORT_CAR_PART_CAR_PATH = "src/main/resources/files/export/car-parts-second.json";
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedCars() throws IOException {
        String fileContent = Files.readString(Path.of(CAR_PATH));
        CarDto[] carDtos = gson.fromJson(fileContent, CarDto[].class);

        for (CarDto carDto : carDtos) {
            if(!validationUtil.isValid(carDto)){
                validationUtil.getViolations(carDto).forEach(vio -> System.out.println(vio.getMessage()));
                continue;
            }
            Car car = modelMapper.map(carDto,Car.class);

            car.setParts(getRandomPart());
            carRepository.saveAndFlush(car);
        }
    }

    @Override
    public void exportCarsFromToyota() throws IOException {
        List<CarToyotaDto> carToyotaDtos = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota").stream()
                .map(car -> modelMapper.map(car, CarToyotaDto.class)).toList();

//        saveToJsonFile(carToyotaDtos,EXPORT_CAR_PATH);
        gson.toJson(carToyotaDtos,new FileWriter(SECOND_EXPORT_CAR_PATH));
        System.out.println();

    }

    @Override
    public void exportCarsWithParts() throws IOException {
        SingleCar singleCar = new SingleCar();
                List<CarAndPartDto> carAndPartDtos = carRepository.findAll().stream().map(car -> {
            CarAndPartDto carAndPartDto = modelMapper.map(car, CarAndPartDto.class);
            List<PartsForCarAndPartDto> partsForCarAndPartDtos = car.getParts().stream()
                    .map(part -> modelMapper.map(part, PartsForCarAndPartDto.class)).collect(Collectors.toList());

            carAndPartDto.setPartsList(partsForCarAndPartDtos);
            return carAndPartDto;
        }).toList();
                singleCar.setCar(carAndPartDtos);
                gson.toJson(singleCar,new FileWriter(SECOND_EXPORT_CAR_PART_CAR_PATH));
    }

//    @Override
//    public void exportCarsWithParts() throws IOException {
//        List<CarAndPartDto> carAndPartDtos = carRepository.findAll().stream().map(car -> {
//            CarAndPartDto carAndPartDto = modelMapper.map(car, CarAndPartDto.class);
//            List<PartsForCarAndPartDto> partsForCarAndPartDtos = car.getParts().stream()
//                    .map(part -> modelMapper.map(part, PartsForCarAndPartDto.class)).collect(Collectors.toList());
//
//            carAndPartDto.setPartsList(partsForCarAndPartDtos);
//            return carAndPartDto;
//        }).toList();
//
//        gson.toJson(carAndPartDtos,new FileWriter(EXPORT_CAR_PART_CAR_PATH));
//
//    }

    private void saveToJsonFile(List<CarToyotaDto> carToyotaDtos, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(carToyotaDtos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Set<Part> getRandomPart() {
        Set<Part> partSet = new HashSet<>();

        int countCars = ThreadLocalRandom.current().nextInt(1,3);

        for (int i = 0; i <countCars ; i++) {
//            long randomId = ThreadLocalRandom.current().nextLong(1,partRepository.count()+1);
//            partSet.add(partRepository.findById(randomId).orElse(null));
//            partSet.add(partRepository.findById(ThreadLocalRandom.current().nextLong(1,partRepository.count()+1)).get());
            partSet.add(partRepository.findById
                    (ThreadLocalRandom.current().nextLong(1,partRepository.count() + 1)).get());
        }


        return partSet;
    }
}
