package com.example.football.service.impl;

import com.example.football.models.dto.StatDto;
import com.example.football.models.dto.StatRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {
    private static final String STAT_FILE_PATH = "D:\\Работен плот\\Spring Data Projects\\Player Finder Alone\\skeleton\\src\\main\\resources\\files\\xml\\stats.xml";
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final Unmarshaller unmarshaller;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) throws JAXBException {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;

        JAXBContext jaxbContext = JAXBContext.newInstance(StatRootDto.class);
        this.unmarshaller = jaxbContext.createUnmarshaller();

    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STAT_FILE_PATH));
    }


//    @Override
//    public String importStats() throws JAXBException, FileNotFoundException {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        xmlParser.readFromFile(STAT_FILE_PATH, StatRootDto.class).getStats()
//                .stream().filter(statDto -> {
////                    Optional<Stat> optStat = statRepository.findByShootingAndPassingAndEndurance(dto.getShooting(),dto.getPassing(),dto.getEndurance());
//                 Optional<Stat> optStat = statRepository.findByShootingAndPassingAndEndurance(statDto.getShooting(),statDto.getPassing(),statDto.getEndurance());
//                    boolean isValid = validationUtil.isValid(statDto) && optStat.isEmpty();
//
//                  stringBuilder.append(isValid ? String.format("Successfully imported Stat %.2f - %.2f - %.2f",
//                        statDto.getPassing(),statDto.getShooting(),statDto.getEndurance())
//                          : "Invalid Stat").append(System.lineSeparator());
//
//                  return isValid;
//                }).map(statDto -> {
//                      Stat stat = modelMapper.map(statDto,Stat.class);
//                    return stat;
//                }).forEach(statRepository::save);
//
//        return stringBuilder.toString();
//    }
    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
//        StatRootDto statRootDto = xmlParser.readFromFile(STAT_FILE_PATH, StatRootDto.class);
        StatRootDto statRootDto = (StatRootDto) unmarshaller.unmarshal(new FileReader(STAT_FILE_PATH));

        for (StatDto statDto : statRootDto.getStats()) {
            Optional<Stat> optStat = statRepository.findByShootingAndPassingAndEndurance(statDto.getPassing(),statDto.getShooting(),statDto.getEndurance());
            boolean isValid = validationUtil.isValid(statDto) && optStat.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                        statDto.getPassing(),statDto.getShooting(),statDto.getEndurance())
                    : "Invalid Stat").append(System.lineSeparator());

            Stat stat = modelMapper.map(statDto,Stat.class);
            statRepository.save(stat);
        }

        return stringBuilder.toString();

  }

}
