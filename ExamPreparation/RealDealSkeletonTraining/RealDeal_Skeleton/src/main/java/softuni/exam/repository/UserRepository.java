package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//Get all users, who have at least 1 item sold with a buyer. Order them by last name, then by first name. Select the person's first and last name.
// For each of the products sold (products with buyers), select the product's name, price and the buyer's first and last name.

//    @Query("SELECT u FROM User u WHERE ((SELECT COUNT(p) FROM Products p WHERE p.seller.id = u.id AND p.buyer IS NOT NULL) > 0) ORDER BY u.lastName,u.firstName")
    @Query("SELECT u FROM User u WHERE " + "(SELECT COUNT(p) FROM Products p WHERE p.seller.id = u.id AND p.buyer IS NOT NULL) > 0 ")
//    @Query("SELECT u FROM User u "+ "JOIN Products p ON u.id=p.seller.id "+ "WHERE p.buyer.id IS NOT NULL "+"GROUP BY u.id "+"HAVING COUNT (p.id) > 0")
    List<User>findAllUsersWithMoreThanOneSoldProduct();
}
