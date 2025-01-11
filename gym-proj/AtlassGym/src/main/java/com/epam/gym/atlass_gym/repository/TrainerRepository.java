package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Trainer;

import java.util.Optional;

public interface TrainerRepository {
    Optional<Trainer> save(Trainer trainer);

    Trainer getTrainerByUsername(String username);
}
