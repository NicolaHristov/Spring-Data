package bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findAllByOrderByBirthdateAscIsYoungDriver();
}
