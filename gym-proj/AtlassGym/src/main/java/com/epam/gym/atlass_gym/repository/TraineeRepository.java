package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Trainee;

import java.util.Optional;

public interface TraineeRepository {
    Optional<Trainee> save(Trainee trainee);

    Trainee getTraineeByUsername(String username);

    void deleteTrainee(Trainee trainee);
}
