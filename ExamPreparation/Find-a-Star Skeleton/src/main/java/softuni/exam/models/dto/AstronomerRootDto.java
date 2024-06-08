package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerRootDto {

    @XmlElement(name = "astronomer")
    private List<AstronomerDto> astronomers;

    public List<AstronomerDto> getAstronomers() {
        return astronomers;
    }

    public void setAstronomers(List<AstronomerDto> astronomers) {
        this.astronomers = astronomers;
    }
}
