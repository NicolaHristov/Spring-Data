package bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Long> {
}
