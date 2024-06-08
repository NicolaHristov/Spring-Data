package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Sale;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CustomerOrderDto {

    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String birthdate;
    @Expose
    private boolean isYoungDriver;
//    @Expose
//    private List<Sale> sales;

    public CustomerOrderDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
