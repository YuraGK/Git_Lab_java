package com.epam.gym.atlass_gym.model.mapped;

import com.epam.gym.atlass_gym.model.Trainer;

import java.util.List;

public class TraineesTrainersList {
    private String login;
    private List<Trainer> trainers;

    public TraineesTrainersList(String login, List<Trainer> trainers) {
        this.login = login;
        this.trainers = trainers;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}
