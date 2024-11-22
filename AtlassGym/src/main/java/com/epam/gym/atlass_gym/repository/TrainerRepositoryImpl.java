package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

public class TrainerRepositoryImpl implements TrainerRepository {

    EntityManager entityManager;
    @Autowired
    TraineeRepositoryImpl traineeRepository;
    private String username;
    private String password;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TrainerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TrainerRepositoryImpl(EntityManager entityManager, String username, String password) {
        this.entityManager = entityManager;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Trainer> save(Trainer trainer) {
        try {
            entityManager.getTransaction().begin();
            if (getTrainerByUsername(trainer.getUsername()) == null) {
                entityManager.merge(trainer);
            } else {
                if (authentificate(trainer.getUsername(), trainer.getPassword())) {
                    Long id = getTrainerByUsername(trainer.getUsername()).getId();
                    trainer.setId(id);
                    trainer = entityManager.merge(trainer);
                }
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


        Query nativeQuery = entityManager.createNativeQuery("select trainees.user_id, users.firstname, users.lastname, trainees.address, trainees.dateofbirth, users.active, users.password, users.username from trainees \n" +
                "inner join users on trainees.user_id = users.user_id\n" +
                "where username = :username");
        nativeQuery.setParameter("username", username);
        List<Object[]> resultList = nativeQuery.getResultList();
        List<Trainee> trainees = new ArrayList<Trainee>();
        for (Object[] list : resultList) {

            Trainee temp = new Trainee(list[1].toString(), list[2].toString(), list[7].toString(), LocalDate.parse(list[4].toString()), list[3].toString(), Long.parseLong(list[0].toString()));
            if (list[5].toString() == "false") temp.toggleActive();
            temp.setPassword(list[6].toString());
            trainees.add(temp);
        }

        Long traineeId = trainees.get(0).getId();


        String stringQuery = "select trainers.user_id, users.firstname, users.lastname, training_types.training_type, users.active, users.password, users.username from trainers\n" +
                "inner join users on trainers.user_id = users.user_id\n" +
                "inner join training_types on trainers.specialisation_training_type_id = training_types.training_type_id";

        Query relationsQuery = entityManager.createNativeQuery("select * from trainee_trainer");
        List<Object[]> relationsList = relationsQuery.getResultList();

        StringJoiner temp = new StringJoiner(" and");

        for (Object[] map : relationsList) {
            if (map[1].toString().equals(traineeId.toString())) {
                temp.add(" trainers.user_id <> " + map[0].toString());
            }

        }
        if (temp.length() > 0) {
            stringQuery = stringQuery + " where" + temp.toString();
        }

        Query nQuery = entityManager.createNativeQuery(stringQuery);

        List<Object[]> rList = nQuery.getResultList();
        return getTrainerListFromObjectArrays(rList);
    }

    private List<String> getStringListFromObjectArrays(List<Object[]> obList) {
        List<String> trainers = new ArrayList<String>();
        for (Object list : obList) {
            trainers.add(list.toString());
        }
        return trainers;
    }

    private List<Long> getIdListFromObjectArrays(List<Object[]> obList) {
        List<Long> trainers = new ArrayList<Long>();
        for (Object list : obList) {
            trainers.add(Long.parseLong(list.toString()));
        }
        return trainers;
    }

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
        if (authentificate(trainer.getUsername(), trainer.getPassword())) {
            trainer.toggleActive();
        }
        return save(trainer);
    }

    public Optional<Trainer> changePasswordByUsername(String username, String newPassword) {
        Trainer trainer = getTrainerByUsername(username);
        if (authentificate(trainer.getUsername(), trainer.getPassword())) {
            trainer.setPassword(newPassword);
            this.password = newPassword;
        }
        return save(trainer);
    }

    private boolean authentificate(String otherUsername, String otherPassword) {
        return username.equals(otherUsername) && password.equals(otherPassword);
    }

    public boolean authentificate(String otherUsername) {
        return username.equals(otherUsername);
    }

    public boolean authorise(String username, String password) {
        Trainer t = getTrainerByUsername(username);
        if (t != null && passwordEncoder.matches(password, t.getPassword())) {
            this.username = username;
            this.password = password;
            return true;
        }
        return false;
    }

}
