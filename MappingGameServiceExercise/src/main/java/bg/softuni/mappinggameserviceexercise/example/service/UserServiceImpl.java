package bg.softuni.mappinggameserviceexercise.example.service;

import bg.softuni.mappinggameserviceexercise.example.model.dto.entity.UserLoginDto;
import bg.softuni.mappinggameserviceexercise.example.model.dto.entity.UserRegisterDto;
import bg.softuni.mappinggameserviceexercise.example.model.entity.User;
import bg.softuni.mappinggameserviceexercise.example.repository.UserRepository;
import bg.softuni.mappinggameserviceexercise.example.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedUserIn;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
          if(!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
              System.out.println("Wrong confirm password");
              return;
          }
        Set<ConstraintViolation<UserRegisterDto>> violations = validationUtil.getViolations(userRegisterDto);

          if(!violations.isEmpty()){
              violations.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
              return;
          }

          User user = modelMapper.map(userRegisterDto, User.class);
          userRepository.save(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil.getViolations(userLoginDto);//Има ли нарушения в userLoginDto // Така си проверявам за грешки и ако ги хвърля

        if(!violations.isEmpty()){
            violations.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            return;
        }

         User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(),userLoginDto.getPassword()).orElse(null);

        if(user == null){
            System.out.println("Incorrect username / password");
            return;
        }

        loggedUserIn = user;

    }

    @Override
    public void logout() {
        if(loggedUserIn == null){
            System.out.println("Cannot log out.No user was logged in.");
        }else{
            loggedUserIn = null;
        }
    }

}
