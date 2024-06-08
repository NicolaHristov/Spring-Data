package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierLocalRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierLocalDto> supplierLocalDtoList;

    public List<SupplierLocalDto> getSupplierLocalDtoList() {
        return supplierLocalDtoList;
    }

    public void setSupplierLocalDtoList(List<SupplierLocalDto> supplierLocalDtoList) {
        this.supplierLocalDtoList = supplierLocalDtoList;
    }
}
