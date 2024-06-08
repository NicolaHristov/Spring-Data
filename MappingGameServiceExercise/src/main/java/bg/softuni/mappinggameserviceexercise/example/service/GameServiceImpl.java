package bg.softuni.mappinggameserviceexercise.example.service;

import bg.softuni.mappinggameserviceexercise.example.model.dto.entity.GameAddDto;
import bg.softuni.mappinggameserviceexercise.example.model.entity.Game;
import bg.softuni.mappinggameserviceexercise.example.repository.GameRepository;
import bg.softuni.mappinggameserviceexercise.example.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
   //1.Проверяваме дали нашето ДТО е валидно
        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.getViolations(gameAddDto);
        //Ако имам нарушения пиша на конзолата какви са проблемите
        if(!violations.isEmpty()){
            violations.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);

        gameRepository.save(game);

        System.out.println("Added game " + gameAddDto.getTitle());

    }

    @Override
    public void editGame(Long gameId, BigDecimal price, Double size) {
      Game game = gameRepository.findById(gameId).orElse(null);

      if(game == null){
          System.out.println("Id is not correct");
          return;
      }

      game.setPrice(price);
//      game.setSize(size);
        gameRepository.save(game);
    }
}
