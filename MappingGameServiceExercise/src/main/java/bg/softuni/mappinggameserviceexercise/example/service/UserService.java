package bg.softuni.mappinggameserviceexercise.example.service;

import bg.softuni.mappinggameserviceexercise.example.model.dto.entity.UserLoginDto;
import bg.softuni.mappinggameserviceexercise.example.model.dto.entity.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();
}
