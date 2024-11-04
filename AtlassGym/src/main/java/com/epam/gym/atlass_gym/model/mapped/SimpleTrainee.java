package com.epam.gym.atlass_gym.model.mapped;

import java.time.LocalDate;

public class SimpleTrainee {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate dateOfBirth;
    private String address;

    public SimpleTrainee(String username, String firstName, String lastName, boolean isActive, LocalDate dateOfBirth, String address) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return isActive;
    }
}
