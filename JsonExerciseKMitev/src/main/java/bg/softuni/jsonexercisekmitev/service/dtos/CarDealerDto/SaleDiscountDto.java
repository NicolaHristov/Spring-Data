package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SaleDiscountDto {
    public CarSaleWithDiscountDto getCarSaleWithDiscountDto() {
        return carSaleWithDiscountDto;
    }

    public void setCarSaleWithDiscountDto(CarSaleWithDiscountDto carSaleWithDiscountDto) {
        this.carSaleWithDiscountDto = carSaleWithDiscountDto;
    }

    @Expose
    private CarSaleWithDiscountDto carSaleWithDiscountDto;
    @Expose
    private String customerName;
    @Expose
    private BigDecimal discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
