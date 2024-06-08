package com.example.football.models.dto;

import com.example.football.models.entity.Town;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class TeamsRootDto {


    private String name;
    private String stadiumName;
    private int fanBase;
    private String history;
    private String townName;

    public TeamsRootDto() {
    }


    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 3)
    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }


    @Min(1000)
    public int getFanBase() {
        return fanBase;
    }

    public void setFanBase(int fanBase) {
        this.fanBase = fanBase;
    }

    @Size(min = 10)
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }


    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
