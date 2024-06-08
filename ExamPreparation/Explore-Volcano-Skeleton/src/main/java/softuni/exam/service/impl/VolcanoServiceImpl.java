package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanosDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private static final String VOLCANOS_FILE_PATH = "src/main/resources/files/json/volcanoes.json";
    private final VolcanoRepository volcanoRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.volcanoRepository = volcanoRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANOS_FILE_PATH));
    }

//    @Override
//    public String importVolcanoes() throws IOException {
//        VolcanosDto[] volcanosDtos = gson.fromJson(readVolcanoesFileContent(), VolcanosDto[].class);
//        StringBuilder stringBuilder = new StringBuilder();
//        for (VolcanosDto volcanosDto : volcanosDtos) {
//            Optional<Volcano> optionalVolcano = volcanoRepository.findByName(volcanosDto.getName());
//            boolean isValid = validationUtil.isValid(volcanosDto) && optionalVolcano.isEmpty();
//
//            stringBuilder.append(isValid ? String.format("Successfully imported volcano %s of type %s",volcanosDto.getName(),volcanosDto.getVolcanoType())
//                    : "Invalid volcano").append(System.lineSeparator());
//
//            if(isValid){
//                Volcano volcano = modelMapper.map(volcanosDto,Volcano.class);
//                volcanoRepository.save(volcano);
//            }
//        }
//        return stringBuilder.toString();
//    }

    @Override
    public String importVolcanoes() throws IOException {
        VolcanosDto[] volcanosDtos = gson.fromJson(readVolcanoesFileContent(), VolcanosDto[].class);
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(volcanosDtos).filter(volcanoDto -> {
            Optional<Volcano> optVolkan = volcanoRepository.findByName(volcanoDto.getName());
            boolean isValid = validationUtil.isValid(volcanoDto) && optVolkan.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported volcano %s of type %s",volcanoDto.getName(),volcanoDto.getVolcanoType())
                    : "Invalid volcano").append(System.lineSeparator());
            return isValid;
        }).map(volcanoDto -> {
            Volcano volcano = modelMapper.map(volcanoDto,Volcano.class);
            return volcano;
        }).forEach(volcanoRepository::save);


        return stringBuilder.toString();
    }

    @Override
    public Optional<Volcano> findByName(String name) {
        return volcanoRepository.findByName(name);
    }

    @Override
    public String exportVolcanoes() {


        return volcanoRepository.findByElevationGreaterThanAndActiveIsTrueAndLastEruptionIsNotNullOrderByElevationDesc().stream()
                .map(volcano -> String.format("Volcano: %s%n" +
                        "   *Located in: %s%n" +
                        "   **Elevation: %d%n" +
                        "   ***Last eruption on: %s%n",
                        volcano.getName(),volcano.getCountry().getName(),
                        volcano.getElevation(),
                        volcano.getLastEruption())).collect(Collectors.joining());
    }

    @Override
    public Volcano findVolcanoByid(Long volcanoId) {
        return volcanoRepository.findById(volcanoId).orElse(null);
    }
}