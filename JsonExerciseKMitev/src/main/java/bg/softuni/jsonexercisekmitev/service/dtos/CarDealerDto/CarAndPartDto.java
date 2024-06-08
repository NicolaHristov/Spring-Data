package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarAndPartDto {

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelledDistance;
    @Expose

    private List<PartsForCarAndPartDto> partsList;

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

    public List<PartsForCarAndPartDto> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<PartsForCarAndPartDto> partsList) {
        this.partsList = partsList;
    }
}
