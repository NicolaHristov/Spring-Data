package softuni.exam.models;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Products extends BaseEntity{
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @ManyToOne
    private User seller;
    @ManyToOne
    private User buyer;
    @ManyToMany
    private Set<Categories> categories;



    public Products() {
    }

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

    //Много продукти могат да бъдат продавани от един човек

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }


    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }
}
