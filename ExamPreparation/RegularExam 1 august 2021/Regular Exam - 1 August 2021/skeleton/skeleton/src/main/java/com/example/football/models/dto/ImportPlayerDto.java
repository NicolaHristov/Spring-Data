package com.example.football.models.dto;

import com.example.football.models.entity.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerDto {


    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;
    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;
    @XmlElement
    @Email
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement
    private Position playerPosition;

    @XmlElement(name = "town")
    private NameDto town;

    @XmlElement(name = "team")
    private NameDto team;

    @XmlElement(name = "stat")
    private StatIdDto stat;

    public ImportPlayerDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public StatIdDto getStat() {
        return stat;
    }

    public void setStat(StatIdDto stat) {
        this.stat = stat;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }
}
