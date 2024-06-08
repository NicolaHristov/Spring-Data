package softuni.exam.models;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column
    private Integer age;
    @ManyToMany
    private Set<User> friends;
    @ManyToMany
    private Set<Products> soldProduct;


    public User() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }


    public Set<Products> getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(Set<Products> soldProduct) {
        this.soldProduct = soldProduct;
    }
}
