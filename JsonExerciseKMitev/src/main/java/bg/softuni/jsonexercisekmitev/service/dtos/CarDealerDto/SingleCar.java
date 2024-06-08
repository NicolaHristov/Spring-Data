package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SingleCar {
    @Expose
    private List<CarAndPartDto> car;

    public List<CarAndPartDto> getCar() {
        return car;
    }

    public void setCar(List<CarAndPartDto> car) {
        this.car = car;
    }
}
