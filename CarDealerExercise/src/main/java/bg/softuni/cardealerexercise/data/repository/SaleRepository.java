package bg.softuni.cardealerexercise.data.repository;

import bg.softuni.cardealerexercise.data.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
}
