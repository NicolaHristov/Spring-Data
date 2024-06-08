package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private static final String CONSTELATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";

    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        ConstellationDto[] constellationDtos = gson.fromJson(readConstellationsFromFile(), ConstellationDto[].class);
        StringBuilder stringBuilder = new StringBuilder();

        for (ConstellationDto constellationDto : constellationDtos) {
            Optional<Constellation> optConstallation = constellationRepository.findByName(constellationDto.getName());
            boolean isValid = validationUtil.isValid(constellationDto) && optConstallation.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported constellation %s - %s",constellationDto.getName(),constellationDto.getDescription())
             : "Invalid constellation").append(System.lineSeparator());

            if(isValid){
                Constellation constellation =modelMapper.map(constellationDto,Constellation.class);
                constellationRepository.save(constellation);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Constellation findByName(Constellation constellation) {
        return constellationRepository.findByName(constellation.getName()).orElse(null);
    }
}
