package com.WorkloadService.workload_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(value = "workload")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Workload {
    @Id
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private LocalDate date;
    private double duration;


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
