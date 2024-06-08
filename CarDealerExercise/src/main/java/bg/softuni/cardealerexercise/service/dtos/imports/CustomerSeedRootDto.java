package bg.softuni.cardealerexercise.service.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto {

    @XmlElement(name = "customer")

    private List<CustomerSeedDto> customerSeedDtoList;

    public List<CustomerSeedDto> getCustomerSeedDtoList() {
        return customerSeedDtoList;
    }

    public void setCustomerSeedDtoList(List<CustomerSeedDto> customerSeedDtoList) {
        this.customerSeedDtoList = customerSeedDtoList;
    }
}
