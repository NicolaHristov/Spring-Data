package bg.softuni.jsonexercise2.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    private Integer age;
    @ManyToMany
    private Set<User>friends;
    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
    private Set<Products>soldProducts;


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

    public Set<Products> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<Products> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
