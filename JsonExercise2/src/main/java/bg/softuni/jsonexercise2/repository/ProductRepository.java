package bg.softuni.jsonexercise2.repository;

import bg.softuni.jsonexercise2.model.dto.ProductNameAndPriceDto;
import bg.softuni.jsonexercise2.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {

    List<Products>findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal lower, BigDecimal upper);
}
