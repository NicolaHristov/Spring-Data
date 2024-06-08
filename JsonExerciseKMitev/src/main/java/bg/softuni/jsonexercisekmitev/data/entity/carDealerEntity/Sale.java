package bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity;

import bg.softuni.jsonexercisekmitev.data.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @Column
    private double discount;
    @ManyToOne(fetch = FetchType.EAGER)//Фетча го добавих на 24.05 в 15:05
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    private Car car;
    @ManyToOne(fetch = FetchType.EAGER)//Фетча го добавих на 24.05 в 15:05
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
