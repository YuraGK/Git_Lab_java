package com.warehouse.tmp.module;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@Data
public class Workload implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private LocalDate date;
    private double duration;

    public Workload() {
    }

    public Workload(int id,
                    String username, String firstName,
                    String lastName, boolean active,
                    LocalDate date,
                    double duration) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.date = date;
        this.duration = duration;
    }

    @JsonCreator
    public Workload(@JsonProperty("username") String username, @JsonProperty("firstName") String firstName,
                    @JsonProperty("lastName") String lastName, @JsonProperty("active") boolean active,
                    @JsonProperty("date") LocalDate date,
                    @JsonProperty("duration") double duration) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.date = date;
        this.duration = duration;
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
        return active;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getDuration() {
        return duration;
    }
}
