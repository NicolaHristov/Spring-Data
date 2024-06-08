package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stars")
public class Star extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String name;
    @Column(name = "light_years",nullable = false)
    private Double lightYears;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;
    @Column(name = "star_type",nullable = false)
    @Enumerated( value = EnumType.STRING)
    private StarType starType;
    @ManyToOne
    @JoinColumn(name = "constellation_id",referencedColumnName = "id")
    private Constellation constellation;
//    @OneToMany
    @OneToMany(mappedBy = "observingStar")
    private Set<Astronomer> observers;

    public Star() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StarType getStarType() {
        return starType;
    }

    public void setStarType(StarType starType) {
        this.starType = starType;
    }

    public Set<Astronomer> getObservers() {
        return observers;
    }

    public void setObservers(Set<Astronomer> observers) {
        this.observers = observers;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }
}
