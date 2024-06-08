package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolLogisticRootDto {


    @XmlElement(name = "volcanologist")
    private List<VolLogistiDto>volcanologists;

    public List<VolLogistiDto> getVolcanologists() {
        return volcanologists;
    }

    public void setVolcanologists(List<VolLogistiDto> volcanologists) {
        this.volcanologists = volcanologists;
    }
}
