package softuni.exam.service;

import softuni.exam.models.User;
import softuni.exam.models.dto.UsersDto;
import softuni.exam.models.dto.users2dto.UserViewRootDto;

import java.util.List;

public interface UserService {

    long getCount();

    void seedUsers(List<UsersDto> users);

    User getRandomUser();

    UserViewRootDto findSuccessfullySold();
}
