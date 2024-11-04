package com.epam.gym.atlass_gym.controller;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.mapped.ActiveUser;
import com.epam.gym.atlass_gym.model.mapped.LoginPassword;
import com.epam.gym.atlass_gym.model.mapped.SimpleTrainee;
import com.epam.gym.atlass_gym.model.mapped.TraineesTrainersList;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.TraineeService;
import com.epam.gym.atlass_gym.service.TrainerService;
import com.epam.gym.atlass_gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/gym/trainee", consumes = {"application/JSON"})
public class TraineeController {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    @Autowired
    private TrainingRepositoryImpl trainingRepository;

    private Logger logger = LoggerFactory.getLogger(TraineeController.class);

    @PostMapping(value = "/register")
    public LoginPassword register(@RequestBody Trainee trainee) {
        if (trainee == null || trainee.getFirstName() == null || trainee.getLastName() == null) {
            logger.warn("Insufficient data, missing first or last name");
            return null;
        }
        Trainee outTrainee = traineeService.createTrainee(trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress());
        traineeRepository.save(outTrainee);
        return new LoginPassword(outTrainee.getUsername(), outTrainee.getPassword());
    }

    @GetMapping(value = "/getProfile")
    public Trainee getProfile(@RequestBody String username) {
        if (username == null) {
            logger.warn("Insufficient data, missing username");
            return null;
        }
        System.out.println(username);
        Trainee trainee = null;
        try {
            trainee = traineeService.selectTrainee(username);
        } catch (NoSuchElementException e) {
            trainee = traineeRepository.getTraineeByUsername(username);
        }

        return trainee;
    }

    @PutMapping(value = "/updateProfile")
    public Trainee update(@RequestBody SimpleTrainee newTraineeInfo) {
        if (newTraineeInfo == null || newTraineeInfo.getUsername() == null || newTraineeInfo.getLastName() == null || newTraineeInfo.getFirstName() == null) {
            logger.warn("Insufficient data, missing username, first or last name");
            return null;
        }

        if (traineeRepository.getTraineeByUsername(newTraineeInfo.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return null;
        }
        Trainee t = new Trainee();
        try {

            t = traineeService.selectTrainee(newTraineeInfo.getUsername());
            traineeService.updateTrainee(newTraineeInfo.getUsername(),
                    newTraineeInfo.getFirstName(),
                    newTraineeInfo.getLastName(),
                    (newTraineeInfo.getDateOfBirth() == null) ? t.getDateOfBirth() : newTraineeInfo.getDateOfBirth(),
                    (newTraineeInfo.getAddress() == null) ? t.getAddress() : newTraineeInfo.getAddress());
            traineeService.selectTrainee(newTraineeInfo.getUsername()).setActive(newTraineeInfo.isActive());
            t = traineeService.selectTrainee(newTraineeInfo.getUsername());
            traineeRepository.save(t);
        } catch (Exception e) {
            t = traineeRepository.getTraineeByUsername(newTraineeInfo.getUsername());
            t.setFirstName(newTraineeInfo.getFirstName());
            t.setLastName(newTraineeInfo.getLastName());
            t.setAddress((newTraineeInfo.getAddress() == null) ? t.getAddress() : newTraineeInfo.getAddress());
            t.setDateOfBirth((newTraineeInfo.getDateOfBirth() == null) ? t.getDateOfBirth() : newTraineeInfo.getDateOfBirth());
            t.setActive(newTraineeInfo.isActive());
            traineeRepository.save(t);
        }


        return t;
    }

    @DeleteMapping(value = "/deleteProfile")
    public ResponseEntity delete(@RequestBody String username) {
        if (username == null) {
            logger.warn("Insufficient data, missing username");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            traineeService.deleteTrainee(username);
        } catch (Exception e) {

        }
        if (traineeRepository.getTraineeByUsername(username) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        traineeRepository.deleteTrainee(traineeRepository.getTraineeByUsername(username));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAvailableTrainers")
    public List<Trainer> getAvailableTrainers(@RequestBody String traineeUsername) {
        if (traineeUsername == null) {
            logger.warn("Insufficient data, missing username");
            return null;
        }
        System.out.println(traineeUsername);
        return trainerRepository.getAvailableTrainerListByTraineeUsername(traineeUsername);
    }

    @PutMapping(value = "/updateTraineesTrainersList")
    public List<Trainer> updateTraineesTrainersList(@RequestBody TraineesTrainersList traineesTrainersList) {
        System.out.println(traineesTrainersList.getTrainers());
        if (traineesTrainersList == null || traineesTrainersList.getLogin() == null || traineesTrainersList.getTrainers() == null) {
            logger.warn("Insufficient data, missing username or trainers list");
            return null;
        }

        if (traineeRepository.getTraineeByUsername(traineesTrainersList.getLogin()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return null;
        }
        Trainee t = new Trainee();
        try {
            traineeService.selectTrainee(traineesTrainersList.getLogin()).setTrainers(traineesTrainersList.getTrainers());
            t = traineeService.selectTrainee(traineesTrainersList.getLogin());
            traineeRepository.save(t);
        } catch (Exception e) {
            //
            t = traineeRepository.getTraineeByUsername(traineesTrainersList.getLogin());
            t.setTrainers(traineesTrainersList.getTrainers());
            traineeRepository.save(t);

        }

        return traineesTrainersList.getTrainers();
    }

    @GetMapping(value = "/getTraineesTrainingsList")
    public List<Training> getTraineesTrainingsList(@RequestBody String traineeUsername) {
        if (traineeUsername == null) {
            logger.warn("Insufficient data, missing username");
            return null;
        }

        if (traineeRepository.getTraineeByUsername(traineeUsername) == null) {
            logger.warn("Trying to update non-existent trainee");
            return null;
        }
        return traineeRepository.getTrainingsByUsernameAndCriteria(traineeUsername, null, null, null, null);
    }

    @PatchMapping(value = "/toggleActive")
    public ResponseEntity toggleActive(@RequestBody ActiveUser user) {
        System.out.println(user.getUsername() + " " + user.isActive());
        if (user == null || user.getUsername() == null) {
            logger.warn("Insufficient data, missing username");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (traineeRepository.getTraineeByUsername(user.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        boolean userIsActive = traineeRepository.getTraineeByUsername(user.getUsername()).isActive();
        if (user.isActive() != userIsActive) {
            traineeRepository.toggleActiveByUsername(user.getUsername());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
