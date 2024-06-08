package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDto;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_PATH = "src/main/resources/files/users.json";

    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, PictureService pictureService) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
    }


    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
//        UserSeedDto[] userSeedDtos = gson.fromJson(readFromFileContent(), UserSeedDto[].class);
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(gson.fromJson(readFromFileContent(), UserSeedDto[].class))
                .filter(userSeedDto -> {
                    boolean isValid = validationUtil.isValid(userSeedDto) &&
                            !isEntityExist(userSeedDto.getUsername())  &&
                            pictureService.isEntityExist(userSeedDto.getProfilePicture());

                    stringBuilder.append(isValid ?
                            "Successfully import User: "+ userSeedDto.getUsername()
                            : "Invalid user").append(System.lineSeparator());


                    return isValid;
                }).map(userSeedDto -> {
                    User user = modelMapper.map(userSeedDto,User.class);
                    user.setProfilePicture(pictureService.findByPath(userSeedDto.getProfilePicture()));

                    return user;
                }).forEach(userRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isEntityExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder stringBuilder = new StringBuilder();

        userRepository.findAllByPostCountDescThenByUserId().stream()
                .forEach(user -> {
                    stringBuilder.append(String.format("User: username"));
                });
        return stringBuilder.toString();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
