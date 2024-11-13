package com.epam.gym.atlass_gym.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Training_type")
@Table(name = "training_types")
public class Training_type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_type_id")
    private Long id;
    @Column(nullable = false)
    private String training_type;

    public Training_type() {
    }

    public Training_type(String training_type) {
        this.training_type = training_type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTraining_type() {
        return training_type;
    }

    public void setTraining_type(String training_type) {
        this.training_type = training_type;
    }
}
