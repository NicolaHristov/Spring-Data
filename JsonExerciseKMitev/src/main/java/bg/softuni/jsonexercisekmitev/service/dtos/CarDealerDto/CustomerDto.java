package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class CustomerDto {

    @Expose
    private String name;

    @Expose
    private String birthdate;

    @Expose
    private boolean isYoungDriver;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
