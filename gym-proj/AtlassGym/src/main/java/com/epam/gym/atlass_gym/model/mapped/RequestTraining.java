package com.epam.gym.atlass_gym.model.mapped;

import java.time.LocalDate;

public class RequestTraining {
    private String username;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String trainerUsername;
    private String trainingType;

    public RequestTraining(String username, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingType) {
        this.username = username;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.trainerUsername = trainerUsername;
        this.trainingType = trainingType;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public String getTrainingType() {
        return trainingType;
    }
}
