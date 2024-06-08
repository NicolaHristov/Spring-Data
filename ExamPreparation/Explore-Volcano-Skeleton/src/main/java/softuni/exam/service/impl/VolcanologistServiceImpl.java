package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolLogistiDto;
import softuni.exam.models.dto.VolLogisticRootDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {
    private static final String VOLCANOSLOGISTIC_FILE_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;
    private final VolcanoService volcanoService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, VolcanoRepository volcanoRepository, VolcanoService volcanoService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
        this.volcanoService = volcanoService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(VOLCANOSLOGISTIC_FILE_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        VolLogisticRootDto volLogisticRootDto = xmlParser.fromFile(VOLCANOSLOGISTIC_FILE_PATH, VolLogisticRootDto.class);
        StringBuilder stringBuilder = new StringBuilder();

        for (VolLogistiDto volcanologistDto: volLogisticRootDto.getVolcanologists()) {
            Optional<Volcanologist>optionalVolcanologist = volcanologistRepository.findByFirstNameAndLastName(volcanologistDto.getFirstName(),volcanologistDto.getLastName());
            //•	If a volcanologist is exploring volcano that doesn't exist in the DB return "Invalid volcanologist".
            //•	If an volcanologist with the same full name (first name and last name) already exists i
            Volcano volcano = volcanoService.findVolcanoByid(volcanologistDto.getVolcanoId());
            boolean isValid = validationUtil.isValid(volcanologistDto) && optionalVolcanologist.isEmpty() && volcano !=null;


            stringBuilder.append(isValid ? String.format("Successfully imported volcanologist %s %s",volcanologistDto.getFirstName(),volcanologistDto.getLastName())
                    : "Invalid volcanologist").append(System.lineSeparator());

            if(isValid){
                Volcanologist volcanologist = modelMapper.map(volcanologistDto,Volcanologist.class);
                volcanologistRepository.save(volcanologist);
            }
        }

        return stringBuilder.toString();
    }
}