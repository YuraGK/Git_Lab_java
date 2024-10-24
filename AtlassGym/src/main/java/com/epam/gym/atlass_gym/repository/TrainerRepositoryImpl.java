package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training_type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainerRepositoryImpl implements TrainerRepository {

    EntityManager entityManager;

    public TrainerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Trainer> save(Trainer trainer) {
        try {
            entityManager.getTransaction().begin();
            if (trainer.getId() == null) {
                entityManager.persist(trainer);
            } else {
                trainer = entityManager.merge(trainer);
            }
            entityManager.getTransaction().commit();

            return Optional.of(trainer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Trainer getTrainerByUsername(String username) {
        Query nativeQuery = entityManager.createNativeQuery(
                "select trainers.user_id, users.firstname, users.lastname, training_types.training_type, users.active, users.password, users.username from trainers\n" +
                        "inner join users on trainers.user_id = users.user_id\n" +
                        "inner join training_types on trainers.specialisation_training_type_id = training_types.training_type_id\n" +
                        "where username = :username");
        nativeQuery.setParameter("username", username);
        List<Object[]> resultList = nativeQuery.getResultList();
        List<Trainer> trainers = new ArrayList<Trainer>();
        for (Object[] list : resultList) {

            Trainer temp = new Trainer(list[1].toString(), list[2].toString(), list[6].toString(), new Training_type(list[3].toString()), Long.parseLong(list[0].toString()));
            if (list[4].toString() == "false") temp.toggleActive();
            temp.setPassword(list[5].toString());
            for (Object st : list) {
                System.out.print(st.toString() + " ");
            }
            trainers.add(temp);
            System.out.println();
        }


        return (trainers.size() == 0) ? null : trainers.get(0);
    }

    public List<Trainer> getAvailableTrainerListByUsername(String username) {

        Query nativeQuery = entityManager.createNativeQuery(
                "select trainers.user_id, users.firstname, users.lastname, training_types.training_type, users.active, users.password, users.username from trainers\n" +
                        "inner join users on trainers.user_id = users.user_id\n" +
                        "inner join training_types on trainers.specialisation_training_type_id = training_types.training_type_id\n" +
                        "where username = :username");
        nativeQuery.setParameter("username", username);

        List<Trainer> trainers = new ArrayList<Trainer>();
        return trainers;
    }
            /*
            17. Get trainers list that not assigned on trainee by trainee's username.
            */

}
