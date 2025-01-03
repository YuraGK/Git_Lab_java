package com.WorkloadService.workload_service.model;

import java.util.List;


public class TrainersMonthlyTrainings {

    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<List> years;

    public TrainersMonthlyTrainings(){}
    public TrainersMonthlyTrainings(String username, String firstName, String lastName, boolean isActive, List<List> years) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.years = years;
    }
}
