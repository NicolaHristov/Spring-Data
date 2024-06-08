package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{

    @OneToMany(mappedBy = "sale",fetch = FetchType.EAGER)
    private Set<Device> devices ;

    @Column(columnDefinition = "bit")
    private boolean discounted;
    @Column(unique = true,nullable = false)
    private String number;
    @Column(name = "sale_date",nullable = false)
    private Date saleDate;
    @ManyToOne
    private Seller seller;

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
}
