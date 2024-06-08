package JSONProcessingEx.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Products extends BaseEntity{

    private String name;
    private BigDecimal price;
    private User seller;
    private User buyer;
    private Set<Categories> categories;



    public Products() {
    }
    @Column
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //Много продукти могат да бъдат продавани от един човек
    @ManyToOne
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
    @ManyToOne
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @ManyToMany
    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }
}
