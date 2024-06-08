package bg.softuni.jsonexercisekmitev.service;

import bg.softuni.jsonexercisekmitev.service.dtos.UserSoldProductsDto;
import bg.softuni.jsonexercisekmitev.service.dtos.dtoQuery4.UserAndProductDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;

    List<UserSoldProductsDto> getAllUsersAndSoldItem();

    void printAllUsersAndSoldProducts();

    UserAndProductDto getUsersAndProductsDto();

    void printGetUserAndProduct();
}
