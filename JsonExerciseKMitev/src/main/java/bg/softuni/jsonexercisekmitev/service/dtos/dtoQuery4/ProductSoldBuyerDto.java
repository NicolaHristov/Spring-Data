package bg.softuni.jsonexercisekmitev.service.dtos.dtoQuery4;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class ProductSoldBuyerDto implements Serializable {

    @Expose
    private int count;
    @Expose
    private List<ProductInfoDto> products;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductInfoDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoDto> products) {
        this.products = products;
    }
}
