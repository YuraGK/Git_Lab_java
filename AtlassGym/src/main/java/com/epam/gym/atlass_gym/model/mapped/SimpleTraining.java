package com.epam.gym.atlass_gym.model.mapped;

import java.time.LocalDate;


public class SimpleTraining {
    private String trainee;
    private String trainer;
    private String trainingName;
    private LocalDate trainingDate;
    private Long trainingDuration;

    public SimpleTraining() {
    }

    public String getTrainingName() {
        return trainingName;
    }

    public Long getTrainingDuration() {
        return trainingDuration;
    }

    public String getTrainee() {
        return trainee;
    }

    public String getTrainer() {
        return trainer;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

}
