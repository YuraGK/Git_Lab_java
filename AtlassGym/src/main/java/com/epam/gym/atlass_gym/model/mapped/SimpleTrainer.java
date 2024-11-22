package com.epam.gym.atlass_gym.model.mapped;

import com.epam.gym.atlass_gym.model.Training_type;

public class SimpleTrainer {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Training_type specialization;

    public SimpleTrainer(String username, String firstName, String lastName, boolean isActive, Training_type specialization) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public Training_type getSpecialization() {
        return specialization;
    }
}
