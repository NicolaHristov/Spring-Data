package bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto;

import com.google.gson.annotations.Expose;

public class LocalSupplierDto {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private int partsCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
