package softuni.exam.service;

import softuni.exam.models.User;
import softuni.exam.models.dto.ProductDto;
import softuni.exam.models.dto.ProductViewRootDto;

import java.util.List;

public interface ProductService {
    long getCount();

    void seedProducts(List<ProductDto> products);


    ProductViewRootDto findProductsInRangeWihtoutBuyer();
}
