package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

public class CarSaleWithDiscountDto {


    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelledDistance;


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


}
