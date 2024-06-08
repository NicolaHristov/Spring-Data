package bg.softuni.jsonexercise2.service;

import bg.softuni.jsonexercise2.model.dto.UserSoldDto;
import bg.softuni.jsonexercise2.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProduct();
}
