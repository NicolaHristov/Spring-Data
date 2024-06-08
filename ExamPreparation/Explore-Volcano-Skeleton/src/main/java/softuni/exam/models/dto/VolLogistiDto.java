package softuni.exam.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "volcanologist")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolLogistiDto {

    @XmlElement(name = "first_name")
    @Size(min = 2, max =30)
   private String firstName;
    @XmlElement(name = "last_name")
    @Size(min = 2, max =30)
   private String lastName;
    @XmlElement
    @Positive
   private Double salary;
    @XmlElement
    @Min(18)
    @Max(80)
   private int age;
    @XmlElement(name = "exploring_from")
   private String exploringFrom;
    @XmlElement(name = "exploring_volcano_id")
   private Long volcanoId;

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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(String exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public Long getVolcanoId() {
        return volcanoId;
    }

    public void setVolcanoId(Long volcanoId) {
        this.volcanoId = volcanoId;
    }
}
