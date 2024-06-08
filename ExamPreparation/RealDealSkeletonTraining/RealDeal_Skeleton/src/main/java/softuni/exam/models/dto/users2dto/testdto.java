package softuni.exam.models.dto.users2dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sc")
@XmlAccessorType(XmlAccessType.FIELD)
public class testdto {
    @XmlElementWrapper(name = "sold-products")
    private List<ProductsWithBuyerDto> products;
}
