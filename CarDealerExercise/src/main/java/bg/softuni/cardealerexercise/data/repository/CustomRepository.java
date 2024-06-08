package bg.softuni.cardealerexercise.data.repository;

import bg.softuni.cardealerexercise.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CustomRepository extends JpaRepository<Customer,Long> {

//    List<Customer>findAllByBirthdateIsYoungDriverAsc();
    List<Customer>findAllByOrderByBirthdateAscIsYoungDriver();

    @Query("SELECT c FROM Customer c WHERE SIZE(c.sales) > 0")
    Set<Customer> findAllWithBoughtCars();


}
