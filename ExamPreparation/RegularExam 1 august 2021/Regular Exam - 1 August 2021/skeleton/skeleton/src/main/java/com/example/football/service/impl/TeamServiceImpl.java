package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAM_PATH_RESOURCES = "D:\\Работен плот\\Spring Data Projects\\Regular Exam - 1 August 2021\\skeleton\\skeleton\\src\\main\\resources\\files\\json\\teams.json";
    private final TeamRepository teamRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        gson = new GsonBuilder().create();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        modelMapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAM_PATH_RESOURCES));
    }

    @Override
    public String importTeams() throws IOException {
        String json = this.readTeamsFileContent();

        TeamImportDto[] teamsDtos = gson.fromJson(json, TeamImportDto[].class);

        return   Arrays.stream(teamsDtos).map(this::importTeam).collect(Collectors.joining("\n"));
        
    }

    private String importTeam(TeamImportDto dto) {
        Set<ConstraintViolation<TeamImportDto>> errors = validator.validate(dto);

        if(!errors.isEmpty()){
            return "Invalid team";
        }

        Optional<Team> optTeam = teamRepository.findByName(dto.getName());

        if(optTeam.isPresent()){
            return "Invalid team";
        }

        Team team = modelMapper.map(dto,Team.class);
        Optional<Town> town = townRepository.findByName(dto.getTownName());

        team.setTown(town.get());
        teamRepository.save(team);

        return "Successfully imported Team "+team;


    }
}
