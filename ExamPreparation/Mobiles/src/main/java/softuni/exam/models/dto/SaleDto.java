package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaleDto {

    @Expose
    private boolean discounted;
    @Expose
    @NotNull
    @Size(min = 7)
    private String number;
    @Expose
    private String saleDate;
    @Expose
    private long seller;

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public long getSeller() {
        return seller;
    }

    public void setSeller(long seller) {
        this.seller = seller;
    }
}
