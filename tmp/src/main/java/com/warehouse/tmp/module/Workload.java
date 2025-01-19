package com.warehouse.tmp.module;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Data
public class Workload {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate date;
    private double duration;

    public Workload(String username, String firstName, String lastName, boolean active, LocalDate trainingDate, double trainingDuration) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = active;
        this.date = trainingDate;
        this.duration = trainingDuration;
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

    public LocalDate getDate() {
        return date;
    }

    public double getDuration() {
        return duration;
    }
}
