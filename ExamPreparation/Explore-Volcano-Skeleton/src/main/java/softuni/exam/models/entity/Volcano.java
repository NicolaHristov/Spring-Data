package softuni.exam.models.entity;

import softuni.exam.models.enums.VolcanoType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "volcanoes")
public class Volcano extends BaseEntity{


    @Column(unique = true,nullable = false)
    private String name;
    @Column(nullable = false)
    private int elevation;
    @Column
    @Enumerated(EnumType.STRING)
    private VolcanoType volcanoType;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column
    private LocalDate lastEruption;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
//    @OneToMany(mappedBy = "exploringVolcano",fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "exploringVolcano")
    private Set<Volcanologist> volcanologists;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
