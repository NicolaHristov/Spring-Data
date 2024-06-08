package bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Car;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CarRepository extends JpaRepository<Car,Long> {

//    List<Car>findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);



}
