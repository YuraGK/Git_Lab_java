package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Training;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class TrainingRepositoryImpl implements TrainingRepository {

    EntityManager entityManager;

    public TrainingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Training> save(Training training) {
        try {
            entityManager.getTransaction().begin();
            if (training.getId() == null) {
                entityManager.persist(training);
            } else {
                return Optional.empty();
            }
            entityManager.getTransaction().commit();

            return Optional.of(training);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }s
}
