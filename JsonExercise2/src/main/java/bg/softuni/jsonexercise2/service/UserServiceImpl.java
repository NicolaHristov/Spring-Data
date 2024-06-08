package bg.softuni.jsonexercise2.service;

import bg.softuni.jsonexercise2.constants.GlobalConstant;
import bg.softuni.jsonexercise2.model.dto.UserSeedDto;
import bg.softuni.jsonexercise2.model.dto.UserSoldDto;
import bg.softuni.jsonexercise2.model.entity.User;
import bg.softuni.jsonexercise2.repository.UserRepository;
import bg.softuni.jsonexercise2.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
          if(userRepository.count()==0){
              Arrays.stream(gson.fromJson(Files.readString(Path.of(GlobalConstant.RESOURCES_FILE_PATH+USERS_FILE_NAME)), UserSeedDto[].class))
              .filter(validationUtil::isValid).map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                      .forEach(userRepository::save);
          }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1,userRepository.count()+1);
       return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProduct() {
        return userRepository.findAllUsersWithSoldMoreThanOneSoldProductOrderByLastNameThanFirstName()
                .stream().map(user -> modelMapper.map(user, UserSoldDto.class)).collect(Collectors.toList());
    }
}
