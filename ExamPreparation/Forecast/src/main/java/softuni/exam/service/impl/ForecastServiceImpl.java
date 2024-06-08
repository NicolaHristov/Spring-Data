package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastDto;
import softuni.exam.models.dto.ForecastRootDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private static final String FORECASTS_FILE_PATH = "D:\\Работен плот\\Spring Data Projects\\Forecast\\src\\main\\resources\\files\\xml\\forecasts.xml";
    public final String EXPORT_FORECAST = "City: %s:%n" +
            "   \t\t-min temperature: %.2f%n" +
            "   \t\t--max temperature: %.2f%n" +
            "   \t\t---sunrise: %s%n" +
            "----sunset: %s%n";
    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final CityService cityService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, CityService cityService, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        ForecastRootDto forecastRootDto = xmlParser.fromFile(FORECASTS_FILE_PATH, ForecastRootDto.class);
        StringBuilder stringBuilder = new StringBuilder();

        for (ForecastDto forecastDto : forecastRootDto.getForecastDtoList()) {
            //•	If the forecasts for the same day of week of the city already exist in the DB return "Invalid forecast".
            City city =cityService.findById(forecastDto.getCityId());
            Forecast forecast = forecastRepository.findAllByCityAndDayOfWeek(city,forecastDto.getDayOfWeek()).orElse(null);
            boolean isValid = validationUtil.isValid(forecastDto) && forecast == null && city != null;

            stringBuilder.append(isValid ? String.format("Successfully import forecast %s - %.2f",
                    forecastDto.getDayOfWeek(),forecastDto.getMaxTemperature())
                    : "Invalid forecast").append(System.lineSeparator());

            if(isValid){
                //Forecast forecast = modelMapper.map(forecastSeedDto, Forecast.class);
                // City city = cityService.findCityById(forecastSeedDto.getCity());
                // forecast.setCity(city);
                Forecast forecastMap = modelMapper.map(forecastDto,Forecast.class);
                City city1 = cityService.findCityById(forecastDto.getCityId());
                forecastMap.setCity(city1);
//                forecast.setCity(cityService.findCityById(forecastDto.getCityId()));
                forecastRepository.save(forecastMap);
            }

        }

        return stringBuilder.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder stringBuilder = new StringBuilder();

        Set<Forecast> forecasts = forecastRepository.
                findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000L).orElseThrow(NoSuchElementException::new);


       return forecasts.stream().map(forecast -> String.format(EXPORT_FORECAST,forecast.getCity().getCityName(),
                forecast.getMinTemperature(),
                forecast.getMaxTemperature(),
                forecast.getSunrise(),
                        forecast.getSunset())).collect(Collectors.joining(System.lineSeparator()));


    }
}
