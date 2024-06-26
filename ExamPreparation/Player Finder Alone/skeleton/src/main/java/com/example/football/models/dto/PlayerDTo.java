package com.example.football.models.dto;

import com.example.football.models.entity.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerDTo {

    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;
    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;
    @XmlElement
    @Email
    private String email;
    @XmlElement

    private String birthDate;
    @XmlElement

    private Position position;
    @XmlElement

    private NameDto town;
    @XmlElement
    private NameDto team;
    @XmlElement(name = "stat")
    private StatIdDto statId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public NameDto getTown() {
        return town;
    }

    public void setTown(NameDto town) {
        this.town = town;
    }

    public NameDto getTeam() {
        return team;
    }

    public void setTeam(NameDto team) {
        this.team = team;
    }

    public StatIdDto getStatId() {
        return statId;
    }

    public void setStatId(StatIdDto statId) {
        this.statId = statId;
    }


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
