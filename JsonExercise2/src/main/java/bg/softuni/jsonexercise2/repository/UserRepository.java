package bg.softuni.jsonexercise2.repository;

import bg.softuni.jsonexercise2.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u "+
    "WHERE (SELECT COUNT (p) FROM Products p WHERE p.seller.id = u.id) > 0"+
    " ORDER BY u.lastName,u.firstName")
    List<User> findAllUsersWithSoldMoreThanOneSoldProductOrderByLastNameThanFirstName();
}
