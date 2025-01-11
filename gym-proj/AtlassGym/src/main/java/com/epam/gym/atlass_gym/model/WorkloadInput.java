package com.epam.gym.atlass_gym.model;


import java.time.LocalDate;

public class WorkloadInput extends Workload{

    private String type; //(ADD/DELETE)

    public WorkloadInput(String username, String firstName, String lastName, boolean active, LocalDate trainingDate, double trainingDuration, String type) {
    super(username, firstName, lastName, active, trainingDate, trainingDuration);
        this.type=type;
    }

    public String getType() {
        return type;
    }

}
