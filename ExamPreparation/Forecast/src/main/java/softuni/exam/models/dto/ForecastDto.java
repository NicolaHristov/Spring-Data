package softuni.exam.models.dto;

import softuni.exam.models.entity.DayOfWeek;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastDto {


    @XmlElement(name = "day_of_week")
    @NotNull
    private DayOfWeek dayOfWeek;
    @XmlElement(name = "max_temperature")
    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    private float maxTemperature;
    @XmlElement(name = "min_temperature")
    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    private float minTemperature;
    @XmlElement
    @NotNull
    private String sunrise;
    @XmlElement
    @NotNull
    private String sunset;
    @XmlElement(name = "city")
    private Long cityId;

    public ForecastDto() {
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
