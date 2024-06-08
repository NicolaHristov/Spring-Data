package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAnaPartRootDto {
    @XmlElement(name = "car")
    private List<CarAndPartDto> carAndPartDtoList;

    public List<CarAndPartDto> getCarAndPartDtoList() {
        return carAndPartDtoList;
    }

    public void setCarAndPartDtoList(List<CarAndPartDto> carAndPartDtoList) {
        this.carAndPartDtoList = carAndPartDtoList;
    }
}
