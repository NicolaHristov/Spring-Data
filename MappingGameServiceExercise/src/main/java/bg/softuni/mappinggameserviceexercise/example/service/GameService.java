package bg.softuni.mappinggameserviceexercise.example.service;

import bg.softuni.mappinggameserviceexercise.example.model.dto.entity.GameAddDto;

import java.math.BigDecimal;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(Long gameId, BigDecimal price, Double size );
}
