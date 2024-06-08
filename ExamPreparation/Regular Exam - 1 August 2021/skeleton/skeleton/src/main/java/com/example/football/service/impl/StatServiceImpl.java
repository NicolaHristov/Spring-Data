package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDto;
import com.example.football.models.dto.ImportStatRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {

    private static final String STAT_PATH_RESOURCES = "D:\\Работен плот\\Spring Data Projects\\Regular Exam - 1 August 2021\\skeleton\\skeleton\\src\\main\\resources\\files\\xml\\stats.xml";
    private final StatRepository statRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;
        JAXBContext context = JAXBContext.newInstance(ImportStatRootDto.class);
        unmarshaller = context.createUnmarshaller();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STAT_PATH_RESOURCES));
    }

    @Override
    public String importStats() throws FileNotFoundException, JAXBException {
        ImportStatRootDto statDTOs  = (ImportStatRootDto) unmarshaller.unmarshal(new FileReader(STAT_PATH_RESOURCES));

        return statDTOs.getStats().stream().map(this::importStat).collect(Collectors.joining("\n"));
    }

    private String importStat(ImportStatDto dto) {
        Set<ConstraintViolation<ImportStatDto>> errors =
                validator.validate(dto);

        Optional<Stat> optStat = statRepository.findByShootingAndPassingAndEndurance(dto.getShooting(),dto.getPassing(),dto.getEndurance());

        if(optStat.isPresent()){
            return "Invalid Stat";
        }

        Stat stat = modelMapper.map(dto,Stat.class);

        statRepository.save(stat);

        return "Successfully imported Stat " + stat;

    }
}
