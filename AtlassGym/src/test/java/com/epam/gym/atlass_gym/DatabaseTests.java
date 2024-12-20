package com.epam.gym.atlass_gym;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AtlassGymApplication.class}
)
@AutoConfigureMockMvc
public class DatabaseTests {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    private static List<Trainee> generateTrainees() {
        Trainee trainee1 = new Trainee("Mary", "Long", "Mary.Long", LocalDate.of(1999, 5, 5), "Doubai");
        Trainee trainee2 = new Trainee("Mark", "Corn", "Mark.Corn", LocalDate.of(1989, 2, 26), "London");

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee1);
        trainees.add(trainee2);

        return trainees;
    }

    @Test
    @Order(1)
    public void trainingRepositoryTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


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

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    @Order(2)
    public void traineeRepositoryTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        List<Trainee> genTrainees = generateTrainees();
        TraineeRepositoryImpl traineeRepository = new TraineeRepositoryImpl(entityManager, genTrainees.get(0).getUsername(), genTrainees.get(0).getPassword(), true);
        for (Trainee t : genTrainees) {
            traineeRepository.save(t);
        }


        assertDoesNotThrow(() -> traineeRepository.getTraineeByUsername(genTrainees.get(0).getUsername()));
        genTrainees.get(0).setAddress("Box");
        traineeRepository.save(genTrainees.get(0));
        assertTrue(
                (traineeRepository.getTraineeByUsername(
                        genTrainees.get(0).getUsername()
                )
                ).getAddress().equals("Box"));

        traineeRepository.deleteTrainee(genTrainees.get(0));
        assertNull(traineeRepository.getTraineeByUsername(genTrainees.get(0).getUsername()));

        assertNotNull(traineeRepository.getTrainingsByUsernameAndCriteria(genTrainees.get(0).getUsername(), null, null, null, null));
        assertNotNull(traineeRepository.changePasswordByUsername(genTrainees.get(1).getUsername(), "doIt2It"));
        assertNotNull(traineeRepository.toggleActiveByUsername(genTrainees.get(1).getUsername()));

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    @Order(2)
    public void trainerRepositoryTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        List<Trainee> genTrainees = generateTrainees();

        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Mary");
        trainer1.setLastName("Doe");
        trainer1.setUsername("Mary.Doe");
        trainer1.setTrainees(genTrainees);
        //set specialisation

        TrainerRepositoryImpl trainerRepository = new TrainerRepositoryImpl(entityManager, trainer1.getUsername(), trainer1.getPassword());

        Training_type fitness = new Training_type("Fitness");
        trainer1.setSpecialisation(fitness);
        trainerRepository.save(trainer1);


        assertNotNull(trainerRepository.getTrainerByUsername("Mary.Doe"));
        Training_type zoomba = new Training_type("Zoomba");
        trainer1.setSpecialisation(zoomba);

        assertTrue(!trainerRepository.save(trainer1).isEmpty());

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("Mark");
        trainer2.setLastName("Maul");
        trainer2.setUsername("Mark.Maul");
        Training_type strong = new Training_type("Strong");
        trainer2.setSpecialisation(strong);

        assertTrue(!trainerRepository.save(trainer2).isEmpty());
        assertNotNull(trainerRepository.getTrainingsByUsernameAndCriteria("Mary.Doe", null, null, null, null));

        assertNotNull(trainerRepository.changePasswordByUsername("Mark.Maul", "doIt2It"));
        assertNotNull(trainerRepository.toggleActiveByUsername("Mark.Maul"));

        assertTrue(0 < trainerRepository.getAvailableTrainerListByTraineeUsername(genTrainees.get(0).getUsername()).size());
        //trainerRepository.getAvailableTrainerListByTraineeUsername(genTrainees.get(0).getUsername()).size();

        entityManager.close();
        entityManagerFactory.close();


    }
}
