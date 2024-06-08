package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.Products;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Long> {


    List<Products>findAllByPriceBetweenAndBuyerIsNull(BigDecimal lower, BigDecimal upper);
}
