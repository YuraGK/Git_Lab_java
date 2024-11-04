package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
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
    }

    public List<Training_type> getTrainingTypes() {
        Query nativeQuery = entityManager.createNativeQuery("select * from training_types");
        List<Object[]> resultList = nativeQuery.getResultList();
        List<Training_type> types = getTraining_typesListFromObjectArrays(resultList);
        return types;
    }

    private List<Training_type> getTraining_typesListFromObjectArrays(List<Object[]> obList) {
        List<Training_type> types = new ArrayList<Training_type>();
        for (Object[] list : obList) {

            Training_type temp = new Training_type(list[1].toString());
            temp.setId(Long.parseLong(list[0].toString()));

            types.add(temp);
            System.out.println();
        }
        return types;
    }
}
