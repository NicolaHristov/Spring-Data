package bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity;

import bg.softuni.jsonexercisekmitev.data.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(name = "birth_date")
    private LocalDateTime birthdate;
    @Column(name = "is_young_driver")
    private boolean isYoungDriver;

    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private Set<Sale>sales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }


    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
