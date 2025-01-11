package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Training;

import java.util.Optional;

public interface TrainingRepository {
    Optional<Training> save(Training training);
}
