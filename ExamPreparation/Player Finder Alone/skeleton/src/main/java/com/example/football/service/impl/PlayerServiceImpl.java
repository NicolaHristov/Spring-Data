package com.example.football.service.impl;

import com.example.football.models.dto.PlayerDTo;
import com.example.football.models.dto.PlayerRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_PATH_FILE = "D:\\Работен плот\\Spring Data Projects\\Player Finder Alone\\skeleton\\src\\main\\resources\\files\\xml\\players.xml";
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final Unmarshaller unmarshaller;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final TownRepository townRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil, TeamRepository teamRepository, StatRepository statRepository, TownRepository townRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.townRepository = townRepository;
        JAXBContext jaxbContext = JAXBContext.newInstance(PlayerRootDto.class);
        this.unmarshaller = jaxbContext.createUnmarshaller();
    }


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_PATH_FILE));
    }

    @Override
    public String importPlayers() throws FileNotFoundException, JAXBException {
        //•	If the player's email already exists in the DB return "Invalid Player".
        //•	The provided town and team names will always be valid.
        //•	The Stat id referenced to the valid Stat id.

        // НЕ Е РЕШЕНА ПРАВИЛНО !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        StringBuilder stringBuilder = new StringBuilder();
//        PlayerRootDto playerRootDto1 = xmlParser.readFromFile(PLAYERS_PATH_FILE, PlayerRootDto.class);
//        PlayerRootDto playerRootDto = (PlayerRootDto) unmarshaller.unmarshal(new FileReader(PLAYERS_PATH_FILE));
        PlayerRootDto playerRootDto = (PlayerRootDto) unmarshaller.unmarshal(new FileReader(PLAYERS_PATH_FILE));

        for (PlayerDTo playerDto : playerRootDto.getPlayers()) {
            Optional<Player> optEmail = playerRepository.findByEmail(playerDto.getEmail());
            boolean isValid = validationUtil.isValid(playerDto) && optEmail.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully import Player %s - %s"
                    ,playerDto.getFirstName(),playerDto.getPosition())
                    : "Invalid Player").append(System.lineSeparator());

            if(isValid){
                Optional<Team> team = this.teamRepository.findByName(playerDto.getTeam().getName());
                Optional<Town> town = this.townRepository.findByName(playerDto.getTown().getName());
                Optional<Stat> stat = this.statRepository.findById(playerDto.getStatId().getId());

                Player player = modelMapper.map(playerDto,Player.class);


                player.setTeam(team.get());
                player.setTown(town.get());
                player.setStat(stat.get());

                playerRepository.save(player);
            }

        }

        return stringBuilder.toString();
    }



    @Override
    public String exportBestPlayers() {
        return null;
    }
}
