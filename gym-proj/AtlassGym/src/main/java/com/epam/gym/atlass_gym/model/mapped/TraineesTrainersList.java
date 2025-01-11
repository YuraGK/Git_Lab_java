package com.epam.gym.atlass_gym.model.mapped;

import com.epam.gym.atlass_gym.model.Trainer;

public class TraineesTrainersList {
    private String login;
    private Trainer[] trainers;

    public TraineesTrainersList(String login, Trainer[] trainers) {
        this.login = login;
        this.trainers = trainers;
    }

    public String getLogin() {
        return login;
    }

    public Trainer[] getTrainers() {
        return trainers;
    }
}
