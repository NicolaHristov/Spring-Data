package JSONProcessingEx.service;

import JSONProcessingEx.model.entity.User;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();
}
