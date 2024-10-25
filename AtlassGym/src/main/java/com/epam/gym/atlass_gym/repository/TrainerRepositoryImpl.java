package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.Date;
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
        List<Trainer> trainers = getTrainerListFromObjectArrays(resultList);
        return (trainers.size() == 0) ? null : trainers.get(0);
    }

    public List<Trainer> getAvailableTrainerListByTraineeUsername(String username) {

        Query nativeQuery = entityManager.createNativeQuery(
                "select trainers.user_id, users.firstname, users.lastname, training_types.training_type, users.active, users.password, users.username from trainers\n" +
                        "inner join users on trainers.user_id = users.user_id\n" +
                        "inner join training_types on trainers.specialisation_training_type_id = training_types.training_type_id\n" +
                        "inner join trainee_trainer on trainers.user_id = trainee_trainer.trainer_id\n" +
                        "inner join trainees on trainee_trainer.trainee_id = trainees.user_id\n" +
                        "where username != :username AND users.user_id != trainees.user_id AND users.user_id = trainers.user_id\n" +
                        "group by trainers.user_id, users.firstname, users.lastname, training_types.training_type, users.active, users.password, users.username");
        nativeQuery.setParameter("username", username);

        List<Object[]> resultList = nativeQuery.getResultList();

        return getTrainerListFromObjectArrays(resultList);
    }

    //17. Get trainers list that not assigned on trainee by trainee's username.
    private List<Trainer> getTrainerListFromObjectArrays(List<Object[]> obList) {
        List<Trainer> trainers = new ArrayList<Trainer>();
        for (Object[] list : obList) {

            Trainer temp = new Trainer(list[1].toString(), list[2].toString(), list[6].toString(), new Training_type(list[3].toString()), Long.parseLong(list[0].toString()));
            if (list[4].toString() == "false") temp.toggleActive();
            temp.setPassword(list[5].toString());
            for (Object st : list) {
                System.out.print(st.toString() + " ");
            }
            trainers.add(temp);
            System.out.println();
        }
        return trainers;
    }

    public List<Training> getTrainingsByUsernameAndCriteria(String username, Date startDate, Date endDate, String trainer_name, Training_type training_type) {
        String query = "select training_id, trainingdate, trainingduration, trainingname, trainee_user_id, trainer_user_id, trainingtype_training_type_id from trainings\n" +
                "   inner join trainers on trainings.trainee_user_id = trainers.user_id\n" +
                "   inner join users on trainers.user_id = users.user_id\n" +
                "   inner join training_types on trainings.trainingtype_training_type_id = training_types.training_type_id\n" +
                "   where users.username = :username";
        if (startDate != null && endDate != null) {
            query = query + " AND (where trainings.trainingdate > :startDate AND where trainings.trainingdate < :endDate)";
        }
        if (trainer_name != null) {
            query = query + " AND (where trainings.trainingname = :trainer_name)";
        }
        if (training_type != null) {
            query = query + " AND (where training_types.training_type = :training_type)";
        }
        Query jpqlQuery = entityManager.createNativeQuery(query);
        jpqlQuery.setParameter("username", username);
        if (startDate != null && endDate != null) {
            jpqlQuery.setParameter("startDate", startDate);
            jpqlQuery.setParameter("endDate", endDate);
        }
        if (trainer_name != null) {
            jpqlQuery.setParameter("trainer_name", trainer_name);
        }
        if (training_type != null) {
            jpqlQuery.setParameter("training_type", training_type);
        }

        List<Training> trainings = jpqlQuery.getResultList();
        return trainings;
    }

    public Optional<Trainer> toggleActiveByUsername(String username) {
        Trainer trainer = getTrainerByUsername(username);
        trainer.toggleActive();
        return save(trainer);
    }

    public Optional<Trainer> changePasswordByUsername(String username, String newPassword) {
        Trainer trainer = getTrainerByUsername(username);
        trainer.setPassword(newPassword);
        return save(trainer);
    }

}
