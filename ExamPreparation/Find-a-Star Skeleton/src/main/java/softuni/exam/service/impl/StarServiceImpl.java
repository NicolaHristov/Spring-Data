package softuni.exam.service.impl;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarsDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {

    private static final String STARS_FILE_PATH = "src/main/resources/files/json/stars.json";

    private final StarRepository starRepository;
    private final ConstellationService constellationService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public StarServiceImpl(StarRepository starRepository, ConstellationService constellationService, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationService = constellationService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StarsDto[] starsDtos = gson.fromJson(readStarsFileContent(), StarsDto[].class);
        StringBuilder stringBuilder = new StringBuilder();

        for (StarsDto starsDto : starsDtos) {
            Optional<Star> optStar = starRepository.findByName(starsDto.getName());
            boolean isValid = validationUtil.isValid(starsDto) && optStar.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported star %s - %.2f light years",starsDto.getName(),starsDto.getLightYears())
                    : "Invalid star").append(System.lineSeparator());

            if(isValid){
                Star star = modelMapper.map(starsDto,Star.class);
//                Constellation constellation = constellationService.findByName(star.getConstellation());
//                star.setConstellation(constellation);
                starRepository.save(star);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String exportStars() {

        return starRepository.findAllByStarTypeAndObserversNullOrderByLightYears(StarType.RED_GIANT).stream()
                .map(star -> String.format("Star: %s%n" +
                        "   *Distance: %.2f light years%n" +
                        "   **Description: %s%n" +
                        "   ***Constellation: %s%n",
                        star.getName(),
                        star.getLightYears(),
                        star.getDescription(),
                        star.getConstellation().getName())).collect(Collectors.joining());
    }

    @Override
    public Optional<Star> findAStar(Long starId) {
        return starRepository.findById(starId);
    }
}
