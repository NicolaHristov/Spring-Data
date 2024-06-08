package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl  implements CountryService {
    private static final String COUNTRY_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return  countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        CountryDto[] countryDtos = gson.fromJson(readCountriesFromFile(), CountryDto[].class);
        List<String> countryList = new ArrayList<>();

        for (CountryDto countryDto : countryDtos) {
            Optional<Country> optCountry = countryRepository.findByName(countryDto.getName());
            boolean isValid = validationUtil.isValid(countryDto) && optCountry.isEmpty();

            if(isValid){
                String countryToAdd = String.format("Successfully imported country %s - %s",countryDto.getName(),countryDto.getCapital());
                countryList.add(countryToAdd);
                Country country = modelMapper.map(countryDto,Country.class);
                countryRepository.save(country);
            }else{
                countryList.add("Invalid country");
            }

        }



        return String.join("\n", countryList);
    }
}
