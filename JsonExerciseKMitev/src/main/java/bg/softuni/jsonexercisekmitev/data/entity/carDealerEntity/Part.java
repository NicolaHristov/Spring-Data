package bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity;

import bg.softuni.jsonexercisekmitev.data.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private Supplier supplier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
