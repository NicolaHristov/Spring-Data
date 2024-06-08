package bg.softuni.cardealerexercise.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartRootDto {

    @XmlElement(name = "part")
    private List<PartDto> partDtoList;

    public List<PartDto> getPartDtoList() {
        return partDtoList;
    }

    public void setPartDtoList(List<PartDto> partDtoList) {
        this.partDtoList = partDtoList;
    }
}
