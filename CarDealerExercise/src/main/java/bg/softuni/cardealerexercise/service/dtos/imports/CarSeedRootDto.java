package bg.softuni.cardealerexercise.service.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDto {
    @XmlElement(name = "car")
    private List<CarSeedDto> carSeedDtoList;

    public List<CarSeedDto> getCarSeedDtoList() {
        return carSeedDtoList;
    }

    public void setCarSeedDtoList(List<CarSeedDto> carSeedDtoList) {
        this.carSeedDtoList = carSeedDtoList;
    }
}
