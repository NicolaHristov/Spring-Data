package softuni.exam.models.dto.users2dto;

import javax.xml.bind.annotation.*;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsDto {


          @XmlAttribute(name = "first-name")
          private String firstName;
          @XmlAttribute(name = "last-name")
          private String lastName;

//          @XmlElementWrapper(name = "sold-products")
//          @XmlElement(name = "product")
    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<ProductsWithBuyerDto> products;


    public UserWithProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductsWithBuyerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsWithBuyerDto> products) {
        this.products = products;
    }
}
