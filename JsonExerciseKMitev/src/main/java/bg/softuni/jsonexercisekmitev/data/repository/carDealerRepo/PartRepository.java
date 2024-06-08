package bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Car;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part,Long> {
}
