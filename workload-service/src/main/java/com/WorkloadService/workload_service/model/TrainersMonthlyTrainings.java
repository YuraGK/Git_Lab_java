package com.WorkloadService.workload_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainersMonthlyTrainings implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<String> years;

    @Override
    public String toString() {
        return String.format("TrainersMonthlyTrainings{username=%s, firstName=%s, lastName=%s, isActive=%b, " + getYears() + "}", getUsername(), getFirstName(), getLastName(), isActive);
    }

}
