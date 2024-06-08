package bg.softuni.cardealerexercise.service.dtos.exports.totalSales5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountRootDto {

    @XmlElement(name = "sale")
    private List<SaleDiscountDto> saleDiscountDtoList;

    public List<SaleDiscountDto> getSaleDiscountDtoList() {
        return saleDiscountDtoList;
    }

    public void setSaleDiscountDtoList(List<SaleDiscountDto> saleDiscountDtoList) {
        this.saleDiscountDtoList = saleDiscountDtoList;
    }
}
