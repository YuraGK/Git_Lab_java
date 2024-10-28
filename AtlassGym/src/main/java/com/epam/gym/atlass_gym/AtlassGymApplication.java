package com.epam.gym.atlass_gym;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AtlassGymApplication {
    @PersistenceContext
    static
    EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();


        //create a new Employee
        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Mary");
        trainer1.setLastName("Doe");
        trainer1.setUsername("Mary.Doe");

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("John");
        trainer2.setLastName("Pork");
        trainer2.setUsername("John.Pork");
        //set specialisation
        Training_type strong = new Training_type("Strong");
        Training_type fitness = new Training_type("Fitness");
        trainer1.setSpecialisation(fitness);

        trainer2.setSpecialisation(strong);

        //set trainee
        List<Trainee> genTrainees = generateTrainees();

        trainer1.setTrainees(genTrainees);
        trainer2.setTrainees(genTrainees);

        TrainerRepositoryImpl trainerRepository = new TrainerRepositoryImpl(entityManager, trainer1.getUsername(), trainer1.getPassword());

        //save Employees
        trainerRepository.save(trainer1);
        trainerRepository.save(trainer2);

        TrainingRepositoryImpl trainingRepository = new TrainingRepositoryImpl(entityManager);
        Training training = new Training();
        training.setTrainingDate(LocalDate.of(2024, 12, 5));
        training.setTrainingDuration(45L);
        training.setTrainingName("Long Strong");
        training.setTrainingType(strong);
        training.setTrainerIds(trainer2);
        training.setTraineeIds(genTrainees.get(0));
        trainingRepository.save(training);

        SpringApplication.run(AtlassGymApplication.class, args);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static List<Trainee> generateTrainees() {
        Trainee trainee1 = new Trainee("Mary", "Long", "Mary.Long", LocalDate.of(1999, 5, 5), "Doubai");
        Trainee trainee2 = new Trainee("Mark", "Corn", "Mark.Corn", LocalDate.of(1989, 2, 26), "London");

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee1);
        trainees.add(trainee2);

        return trainees;
    }
}
