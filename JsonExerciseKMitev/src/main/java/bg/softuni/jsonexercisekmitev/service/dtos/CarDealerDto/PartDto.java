package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Expose
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
