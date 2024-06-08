package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class CityServiceImpl implements CityService {

    private static final String CITIES_FILE_PATH = "D:\\Работен плот\\Spring Data Projects\\Forecast\\src\\main\\resources\\files\\json\\cities.json";

    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CityServiceImpl(CityRepository cityRepository, CountryService countryService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        CityDto[] cityDtos = gson.fromJson(readCitiesFileContent(), CityDto[].class);
        StringBuilder stringBuilder = new StringBuilder();

        for (CityDto cityDto : cityDtos) {
            //•	If the city name already exists in the DB return "Invalid city".
//            boolean isCityExist = cityRepository.findByCityName(cityDto.getCityName());
            Optional<City> optCity = cityRepository.findByCityName(cityDto.getCityName());
            boolean isValid = validationUtil.isValid(cityDto) && optCity.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported city %s - %d"
                    ,cityDto.getCityName(),cityDto.getPopulation())
                    : "Invalid city").append(System.lineSeparator());

            if(isValid){
                City city = modelMapper.map(cityDto,City.class);
                city.setCountry(countryService.findById(cityDto.getCountry()));
                cityRepository.save(city);
            }

        }
        return stringBuilder.toString();
    }

    @Override
    public City findById(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    @Override
    public City findCityById(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }
}
