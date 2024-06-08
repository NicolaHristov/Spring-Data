package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "devices")
public class Device extends BaseEntity{

    @Column(nullable = false)
    private String brand;
    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @Column(nullable = false,unique = true)
    private String model;

    @Column(name = "price")
    private double price;
    private Integer storage;

    @ManyToOne
    private Sale sale;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
