package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerDto;
import softuni.exam.models.dto.AstronomerRootDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private static final String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";

    private final AstronomerRepository astronomerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final StarService starService;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, StarService starService) {
        this.astronomerRepository = astronomerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.starService = starService;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        AstronomerRootDto astronomerRootDto = xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomerRootDto.class);
        StringBuilder stringBuilder = new StringBuilder();
//•	If an astronaut with the same full name (first name and last name) already exists in the DB return "Invalid astronomer".
//•	If an astronaut is observing star that doesn't exist in the DB return "Invalid astronomer ".
        for (AstronomerDto astronomerDto : astronomerRootDto.getAstronomers()) {
            Optional<Astronomer> optAstro = astronomerRepository.findByFirstNameAndLastName(astronomerDto.getFirstName(),astronomerDto.getLastName());
            Optional<Star> optStar = starService.findAStar(astronomerDto.getStarId());

            boolean isValid = validationUtil.isValid(astronomerDto) && optAstro.isEmpty() && optStar.isPresent();

            stringBuilder.append(isValid ? String.format("Successfully imported astronomer %s %s - %.2f",
                    astronomerDto.getFirstName(),astronomerDto.getLastName(),astronomerDto.getAverageObservationHours())
                    : "Invalid astronomer").append(System.lineSeparator());

            if(isValid){
                Astronomer astronomer = modelMapper.map(astronomerDto,Astronomer.class);
                astronomerRepository.save(astronomer);
            }

        }
        return stringBuilder.toString();
    }
}
