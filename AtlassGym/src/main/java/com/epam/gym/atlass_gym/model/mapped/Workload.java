package com.epam.gym.atlass_gym.model.mapped;

import java.time.LocalDate;


public class Workload {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate date;
    private double duration;
    private String type; //(ADD/DELETE)

    public Workload() {
    }


    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }
}
