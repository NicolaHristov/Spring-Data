package com.example.football.service.impl;

import com.example.football.models.dto.TeamsRootDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAMS_FILE_PATH = "D:\\Работен плот\\Spring Data Projects\\Player Finder Alone\\skeleton\\src\\main\\resources\\files\\json\\teams.json";

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }



    @Override
    public String importTeams() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamsRootDto[].class))
                .filter(teamsRootDto -> {
//                    boolean isTeamExist = teamRepository.findByName(teamsRootDto.getName());
                    Optional<Team> isTeamExistOpt = teamRepository.findByName(teamsRootDto.getName());
                    boolean isValid = validationUtil.isValid(teamsRootDto) && isTeamExistOpt.isEmpty();

                    stringBuilder.append(isValid ? String.format("Successfully import team %s - %d",teamsRootDto.getName(),teamsRootDto.getFanBase())
                            : "Invalid team").append(System.lineSeparator());


                    return isValid;
                }).map(teamsRootDto -> {
                    Team team = modelMapper.map(teamsRootDto,Team.class);
                    Optional<Town> town = townRepository.findByName(teamsRootDto.getTownName());
                    team.setTown(town.get());
                    return team;
                }).forEach(teamRepository::save);

        return stringBuilder.toString();
    }

//    @Override
//    public String importTeams() throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        TeamsRootDto[] teamsRootDtos = gson.fromJson(readTeamsFileContent(), TeamsRootDto[].class);
//
//        for (TeamsRootDto teamsRootDto : teamsRootDtos) {
////            boolean isTeamExist = teamRepository.findByName(teamsRootDto.getName());
//            Optional<Team> isTeamExist = teamRepository.findByName(teamsRootDto.getName());
//            boolean isValid = validationUtil.isValid(teamsRootDto) && isTeamExist.isEmpty();
//
//            stringBuilder.append(isValid ? String.format("Successfully import Team %s %d",teamsRootDto.getName(),teamsRootDto.getFanBase())
//                    : "Invalid Team").append(System.lineSeparator());
//
//            if(isValid){
//                Team team = modelMapper.map(teamsRootDto,Team.class);
//                Optional<Town> town = townRepository.findByName(teamsRootDto.getTownName());
//                team.setTown(town.get());
//                teamRepository.save(team);
//            }
//        }
//
//
//        return stringBuilder.toString();
//    }
}
