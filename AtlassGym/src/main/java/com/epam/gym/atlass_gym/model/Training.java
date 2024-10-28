package com.epam.gym.atlass_gym.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "Training")
@Table(name = "trainings")
public class Training implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long training_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trainer trainer;

    @Column(nullable = false)
    private String trainingName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Training_type trainingType;

    @Column(nullable = false)
    private LocalDate trainingDate;
    @Column(nullable = false)
    private Long trainingDuration;

    public Training() {
    }

    public Training(String trainingName, Training_type trainingType, LocalDate trainingDate, Long trainingDuration) {
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDuration = trainingDuration;
        this.trainingDate = trainingDate;
    }

    public Training(Trainee traineeId, Trainer trainerId, String trainingName, Training_type trainingType, LocalDate trainingDate, Long trainingDuration) {
        this.trainee = traineeId;
        this.trainer = trainerId;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDuration = trainingDuration;
        this.trainingDate = trainingDate;
    }

    public Training(String trainingName, Training_type trainingType, LocalDate trainingDate, Long trainingDuration, Long newId) {
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDuration = trainingDuration;
        this.trainingDate = trainingDate;
        this.training_id = newId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Training_type getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(Training_type trainingType) {
        this.trainingType = trainingType;
    }

    public Long getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Long trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public Trainee getTraineeIds() {
        return trainee;
    }

    public void setTraineeIds(Trainee traineeId) {
        this.trainee = traineeId;
    }

    public Trainer getTrainerIds() {
        return trainer;
    }

    public void setTrainerIds(Trainer trainerId) {
        this.trainer = trainerId;
    }

    @Override
    public String toString() {
        return training_id + " " + (trainee == null ? "" : trainee.getId()) + " " + (trainer == null ? "" : trainer.getId()) + " " + trainingName + " " + trainingType.getTraining_type() + " " + trainingDate.toString() + " " + trainingDuration;

    }

    @Override
    public boolean equals(Object obj) {

        return obj.toString().equals(toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + training_id.intValue();
        result = prime * result + ((trainee == null) ? 0 : trainee.hashCode());
        result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
        result = prime * result + ((trainingName == null) ? 0 : trainingName.hashCode());
        result = prime * result + ((trainingType == null) ? 0 : trainingType.hashCode());
        result = prime * result + ((trainingDate == null) ? 0 : trainingDate.hashCode());
        result = prime * result + trainingDuration.intValue();
        return result;
    }

    public Long getId() {
        return training_id;
    }

    public void setId(Long id) {
        this.training_id = id;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }
}
