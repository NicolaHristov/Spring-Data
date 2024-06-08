package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "devices")
@XmlAccessorType(XmlAccessType.FIELD)
public class DevicesRootDto {


    @XmlElement(name = "device")
    private List<DeviceDto>deviceDtoList;

    public List<DeviceDto> getDeviceDtoList() {
        return deviceDtoList;
    }

    public void setDeviceDtoList(List<DeviceDto> deviceDtoList) {
        this.deviceDtoList = deviceDtoList;
    }
}
