package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.User;
import softuni.exam.models.dto.UsersDto;
import softuni.exam.models.dto.users2dto.ProductsWithBuyerDto;
import softuni.exam.models.dto.users2dto.UserWithProductsDto;
import softuni.exam.models.dto.users2dto.UserViewRootDto;
import softuni.exam.repository.UserRepository;
import softuni.exam.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }

    @Override
    public void seedUsers(List<UsersDto> users) {
        users.stream().filter(validationUtil::isValid)
                .map( usersDto -> modelMapper.map(usersDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1,userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UserViewRootDto findSuccessfullySold() {
        UserViewRootDto rootDto = new UserViewRootDto();

//
//        rootDto.setProductsRoot(userRepository.findAllUsersWithMoreThanOneSoldProduct().stream()
//                .map(user -> modelMapper.map(user, UserWithProductsDto.class)).collect(Collectors.toList()));

//        rootDto.setProductsRoot(userRepository.findAllUsersWithMoreThanOneSoldProduct().stream()
//                .map(user12 -> modelMapper.map(user12, UserWithProductsDto.class)).collect(Collectors.toList()));

        List<UserWithProductsDto> userList = new ArrayList<>();

        for (User user : userRepository.findAllUsersWithMoreThanOneSoldProduct()) {

          UserWithProductsDto userWithProductsDto = modelMapper.map(user,UserWithProductsDto.class);

          userList.add(userWithProductsDto);
        }


        rootDto.setProductsRoot(userList);

        return rootDto;
    }
}
