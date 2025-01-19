package com.warehouse.tmp.module;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class TrainersMonthlyTrainings implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<String> years;

    public TrainersMonthlyTrainings() {
        years = new ArrayList<String>();
    }

    public TrainersMonthlyTrainings(String username, String firstName,
                                    String lastName, boolean isActive, List<String> years) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.years = years;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }


    @Override
    public String toString() {
        return "TrainersMonthlyTrainings(" + username + ", " + firstName + ", " + lastName + ", " + isActive + ", " + years.toString() + ")";
    }

}
