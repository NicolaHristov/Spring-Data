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
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService {
    private static final String COUNTRIES_FILE_PATH = "D:\\Работен плот\\Spring Data Projects\\Forecast\\src\\main\\resources\\files\\json\\countries.json";

    private final CountryRepository countryRepository;
    private  final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        CountryDto[] countryDtos = gson.fromJson(readCountriesFromFile(), CountryDto[].class);
        StringBuilder stringBuilder = new StringBuilder();

        for (CountryDto countryDto : countryDtos) {
            Optional<Country>optCountry = countryRepository.findByCountryName(countryDto.getCountryName());
            boolean isValid = validationUtil.isValid(countryDto) && optCountry.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully import Country %s - %s"
                    ,countryDto.getCountryName(),countryDto.getCurrency())
                    : "Invalid country").append(System.lineSeparator());
            if(isValid){
                Country country = modelMapper.map(countryDto,Country.class);
                countryRepository.save(country);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Country findById(Long countrtId) {
        return this.countryRepository.findById(countrtId).orElse(null);
    }
}
