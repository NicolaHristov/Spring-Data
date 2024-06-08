package com.example.football.service.impl;

import com.example.football.models.dto.ImportPlayerDto;
import com.example.football.models.dto.ImportPlayerRootDto;
import com.example.football.models.dto.ImportStatRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYER_PATH_RESOURCES = "D:\\Работен плот\\Spring Data Projects\\Regular Exam - 1 August 2021\\skeleton\\skeleton\\src\\main\\resources\\files\\xml\\players.xml";
    private final PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private TownRepository townRepository;
    private StatRepository statRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;
//    private final TypeMap<ImportPlayerDto,Player> dtoPlayerTypeMap;


    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, TownRepository townRepository, StatRepository statRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        JAXBContext context = JAXBContext.newInstance(ImportPlayerRootDto.class);
        unmarshaller = context.createUnmarshaller();
        validator = Validation.buildDefaultValidatorFactory().getValidator();


        modelMapper = new ModelMapper();

        modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(),DateTimeFormatter.ofPattern("dd/MM/yyyy")),String.class,LocalDate.class);


//        Converter<String, LocalDate> toLocalDate = TOWA NE RABOTI
//        s -> s.getSource() == null ? null : LocalDate.parse(s.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        modelMapper.addConverter(toLocalDate);
//
//         TypeMap<ImportPlayerDto, Player> typeMap = this.modelMapper.createTypeMap(ImportPlayerDto.class,Player.class);
//         dtoPlayerTypeMap = typeMap.addMappings(map -> map.using(toLocalDate).map(ImportPlayerDto::getBirthDate,Player::setBirthDate));

    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_PATH_RESOURCES));
    }

    @Override
    public String importPlayers() throws FileNotFoundException, JAXBException {
        ImportPlayerRootDto playerDtos = (ImportPlayerRootDto) unmarshaller.unmarshal(new FileReader(PLAYER_PATH_RESOURCES));

        return playerDtos.getPlayers().stream().map(this::importPlayer).collect(Collectors.joining("\n"));
    }

    private String importPlayer(ImportPlayerDto dto) {
        Set<ConstraintViolation<ImportPlayerDto>> errors = validator.validate(dto);

        if(!errors.isEmpty()){
            return "Invalid Player";
        }

        Optional<Player> optPlayer = this.playerRepository.findByEmail(dto.getEmail());
        if(optPlayer.isPresent()){
            return "Invalid Player";
        }

        Optional<Team> team = this.teamRepository.findByName(dto.getTeam().getName());
        Optional<Town> town = this.townRepository.findByName(dto.getTown().getName());
        Optional<Stat> stat = this.statRepository.findById(dto.getStat().getId());

        Player player = modelMapper.map(dto,Player.class);

        player.setTeam(team.get());
        player.setTown(town.get());
        player.setStat(stat.get());

        this.playerRepository.save(player);

        return "Successfully imported Player " + player.getFirstName() + " " + player.getLastName() + " " + player.getPosition().toString();
    }

    @Override
    public String exportBestPlayers() {
        return null;
    }
}
