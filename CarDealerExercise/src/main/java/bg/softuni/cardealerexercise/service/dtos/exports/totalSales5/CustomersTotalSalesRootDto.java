package bg.softuni.cardealerexercise.service.dtos.exports.totalSales5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersTotalSalesRootDto {

    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> customerTotalSalesDtoList;


    public List<CustomerTotalSalesDto> getCustomerTotalSalesDtoList() {
        return customerTotalSalesDtoList;
    }

    public void setCustomerTotalSalesDtoList(List<CustomerTotalSalesDto> customerTotalSalesDtoList) {
        this.customerTotalSalesDtoList = customerTotalSalesDtoList;
    }
}
