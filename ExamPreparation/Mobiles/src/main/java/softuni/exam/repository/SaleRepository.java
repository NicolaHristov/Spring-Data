package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Sale;

import java.util.Optional;

//TODO
public interface SaleRepository extends JpaRepository<Sale,Long> {

//    Optional<Sale>findAllByNumber(String number);

    Optional<Sale> findSaleByNumber(String number);
}
