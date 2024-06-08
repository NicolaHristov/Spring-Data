package bg.softuni.jsonexercisekmitev.data.repository;


import bg.softuni.jsonexercisekmitev.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Set<User> findAllBySoldBuyerIsNotNull();
}
