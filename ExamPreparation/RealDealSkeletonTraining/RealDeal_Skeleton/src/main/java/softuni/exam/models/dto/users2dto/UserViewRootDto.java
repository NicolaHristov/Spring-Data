package softuni.exam.models.dto.users2dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDto {

    @XmlElement(name = "user")
    private List<UserWithProductsDto> productsRoot;

    public List<UserWithProductsDto> getProductsRoot() {
        return productsRoot;
    }

    public void setProductsRoot(List<UserWithProductsDto> productsRoot) {
        this.productsRoot = productsRoot;
    }
}
