package com.epam.gym.atlass_gym.repository;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TraineeRepositoryImpl implements TraineeRepository {

    EntityManager entityManager;
    private String username;
    private String password;
    private boolean isTrainer;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TraineeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TraineeRepositoryImpl(EntityManager entityManager, String username, String password, boolean isTrainer) {
        this.entityManager = entityManager;
        this.username = username;
        this.password = password;
        this.isTrainer = isTrainer;
    }

    @Override
    public Optional<Trainee> save(Trainee trainee) {
        try {
            entityManager.getTransaction().begin();
            if (getTraineeByUsername(trainee.getUsername()) == null) {
                System.out.println("Trainee save");
                entityManager.merge(trainee);
            } else {
                if (isTrainer || authentificate(trainee.getUsername(), trainee.getPassword())) {
                    Long id = getTraineeByUsername(trainee.getUsername()).getId();
                    trainee.setId(id);
                    trainee = entityManager.merge(trainee);
                }
            }
            entityManager.getTransaction().commit();

            return Optional.of(trainee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Trainee getTraineeByUsername(String username) {
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
            System.out.println("Trainee got: " + temp);
        }
        return (trainees.size() == 0) ? null : trainees.get(0);
    }

    @Override
    public void deleteTrainee(Trainee trainee) {
        Trainee t = entityManager.find(Trainee.class, trainee.getId());
        entityManager.getTransaction().begin();
        System.out.println(trainee);

        if (entityManager.contains(t)) {
            System.out.println("remove");
            if (isTrainer || authentificate(trainee.getUsername(), trainee.getPassword())) {
                entityManager.remove(t);
            }
        }

        entityManager.getTransaction().commit();
    }

    public List<Training> getTrainingsByUsernameAndCriteria(String username, Date startDate, Date endDate, String trainer_name, Training_type training_type) {
        String query = "select training_id, trainingdate, trainingduration, trainingname, trainee_user_id, trainer_user_id, trainingtype_training_type_id from trainings\n" +
                "   inner join trainees on trainings.trainee_user_id = trainees.user_id\n" +
                "   inner join users on trainees.user_id = users.user_id\n" +
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

    public Optional<Trainee> toggleActiveByUsername(String username) {
        Trainee trainee = getTraineeByUsername(username);
        if (isTrainer || authentificate(trainee.getUsername(), trainee.getPassword())) {
            trainee.toggleActive();
        }
        return save(trainee);
    }

    public Optional<Trainee> changePasswordByUsername(String username, String newPassword) {
        Trainee trainee = getTraineeByUsername(username);
        if (isTrainer || authentificate(trainee.getUsername(), trainee.getPassword())) {
            trainee.setPassword(newPassword);
            this.password = newPassword;
        }
        return save(trainee);
    }

    private boolean authentificate(String otherUsername, String otherPassword) {
        System.out.println(username + " " + password);
        System.out.println(otherUsername + " " + otherPassword);
        return username.equals(otherUsername) && password.equals(otherPassword);
    }

    public boolean authentificate(String otherUsername) {
        return username.equals(otherUsername);
    }

    public boolean authorise(String username, String rawPassword) {
        System.out.println("authorise " + username + " " + rawPassword);
        Trainee t = getTraineeByUsername(username);
        System.out.println(passwordEncoder.encode(rawPassword));
        if (t != null && passwordEncoder.matches(rawPassword, t.getPassword())) {
            this.username = username;
            this.password = rawPassword;
            this.isTrainer = false;
            return true;
        }
        return false;
    }

    public void logout() {
        this.username = null;
        this.password = null;
        this.isTrainer = false;
    }

    public void setTrainer() {
        this.isTrainer = true;
    }
}
