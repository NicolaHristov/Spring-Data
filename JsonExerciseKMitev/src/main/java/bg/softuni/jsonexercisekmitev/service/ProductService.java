package bg.softuni.jsonexercisekmitev.service;

import bg.softuni.jsonexercisekmitev.service.dtos.ProductInRangeDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

   void seedUsers() throws FileNotFoundException;

   List<ProductInRangeDto> getAllProductsInRange(BigDecimal lower,BigDecimal upper);

   void printAllProductsInRange(BigDecimal from,BigDecimal to);
}
