package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Car;
import bg.softuni.cardealerexercise.data.entities.Part;
import bg.softuni.cardealerexercise.data.repository.CarRepository;
import bg.softuni.cardealerexercise.data.repository.PartRepository;
import bg.softuni.cardealerexercise.service.CarService;
import bg.softuni.cardealerexercise.service.dtos.exports.*;
import bg.softuni.cardealerexercise.service.dtos.imports.CarSeedDto;
import bg.softuni.cardealerexercise.service.dtos.imports.CarSeedRootDto;
import bg.softuni.cardealerexercise.util.ValidationUtil;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String CARS_PATH = "src/main/resources/xml/imports/cars.xml";
    private static final String EXPORT_CARS = "src/main/resources/xml/exports/toyota-cars.xml";
    private static final String EXPORT_CARS_AND_PARTS = "src/main/resources/xml/exports/cars-and-parts.xml";

    private  final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCars() throws JAXBException {
        if(carRepository.count() == 0){
            CarSeedRootDto carSeedRootDto = xmlParser.fromFile(CarSeedRootDto.class, CARS_PATH);

            for (CarSeedDto carSeedDto : carSeedRootDto.getCarSeedDtoList()) {
                if(!validationUtil.isValid(carSeedDto)){
                    System.out.println("Invalid car");
                    continue;
                }
                Car car = modelMapper.map(carSeedDto, Car.class);
                car.setParts(getParts());
                carRepository.save(car);

            }
            System.out.println();
        }
    }

    @Override
    public void exportCarsFromToyota() throws JAXBException {
        List<CarToyotaDto> toyota = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream().map(car -> modelMapper.map(car, CarToyotaDto.class)).collect(Collectors.toList());

        CarToyotaRootDto carToyotaRootDto = new CarToyotaRootDto();
        carToyotaRootDto.setCarToyotaDtoList(toyota);

        xmlParser.exportToFile(CarToyotaRootDto.class,carToyotaRootDto,EXPORT_CARS);
    }

//    @Override
//    public void exportCarAndParts() throws JAXBException {
//        //Get all cars along with their list of parts.
//        // For the car get only make, model and travelled distance and for the parts get only name and price
//        List<CarAndPartDto> carAndPartDtos = carRepository.findAll().stream().map(car -> {
//            CarAndPartDto carAndPartDto = modelMapper.map(car, CarAndPartDto.class);
//
//            PartRootDto partRootDto = new PartRootDto();
//            List<PartDto> partDtoList = car.getParts().stream().map(carMap -> modelMapper.map(carMap, PartDto.class)).toList();
//
//            partRootDto.setPartDtoList(partDtoList);
//            carAndPartDto.setPartRootDto(partRootDto);
//
//            return carAndPartDto;
//        }).collect(Collectors.toList());
//
//        CarAnaPartRootDto carAnaPartRootDto = new CarAnaPartRootDto();
//        carAnaPartRootDto.setCarAndPartDtoList(carAndPartDtos);
//    }


    @Override
    public void exportCarAndParts() throws JAXBException {
        //Get all cars along with their list of parts.
        //For the car get only make, model and travelled distance and for the parts get only name and price
        List<CarAndPartDto> carAndPartDtos = this.carRepository.findAll().stream().
                map(car -> {
                    CarAndPartDto carAndPartDto = modelMapper.map(car, CarAndPartDto.class);

                    PartRootDto partRootDto = new PartRootDto();
                    List<PartDto> partDtoList = car.getParts().stream().map(part -> modelMapper.map(part, PartDto.class)).collect(Collectors.toList());
                    partRootDto.setPartDtoList(partDtoList);

                    carAndPartDto.setPartRootDto(partRootDto);

                    return carAndPartDto;
                }).collect(Collectors.toList());

        CarAnaPartRootDto carAnaPartRootDto = new CarAnaPartRootDto();
        carAnaPartRootDto.setCarAndPartDtoList(carAndPartDtos);

        xmlParser.exportToFile(CarAnaPartRootDto.class,carAnaPartRootDto,EXPORT_CARS_AND_PARTS);

    }


    private Set<Part> getParts() {
        Set<Part> partSet = new HashSet<>();

        int count = ThreadLocalRandom.current().nextInt(2,4);

        for (int i = 0; i <count ; i++) {
            partSet.add(partRepository.findById
            (ThreadLocalRandom.current().nextLong(1,partRepository.count() + 1)).get());
        }

        return partSet;
    }
}
