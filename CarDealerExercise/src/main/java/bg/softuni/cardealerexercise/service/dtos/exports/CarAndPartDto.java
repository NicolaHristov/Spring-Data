package bg.softuni.cardealerexercise.service.dtos.exports;

import bg.softuni.cardealerexercise.data.entities.Part;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartDto {


    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute
    private long travelledDistance;

    @XmlElement(name = "parts")
    private PartRootDto partRootDto;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartRootDto getPartRootDto() {
        return partRootDto;
    }

    public void setPartRootDto(PartRootDto partRootDto) {
        this.partRootDto = partRootDto;
    }
}
