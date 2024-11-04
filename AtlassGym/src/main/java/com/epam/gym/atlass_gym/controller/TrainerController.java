package com.epam.gym.atlass_gym.controller;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.mapped.ActiveUser;
import com.epam.gym.atlass_gym.model.mapped.LoginPassword;
import com.epam.gym.atlass_gym.model.mapped.SimpleTrainer;
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
@RequestMapping(value = "/gym/trainer", consumes = {"application/JSON"})
public class TrainerController {

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
    private Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @PostMapping(value = "/register")
    public LoginPassword register(@RequestBody Trainer trainer) {
        trainer.setUsername(trainer.getFirstName() + "." + trainer.getLastName());
        if (trainer == null || trainer.getFirstName() == null || trainer.getLastName() == null) {
            logger.warn("Insufficient data, missing first or last name");
            return null;
        }
        Trainer outTrainer = trainerService.createTrainer(trainer.getFirstName(),
                trainer.getLastName(), trainer.getSpecialisation());
        trainerRepository.save(outTrainer);
        return new LoginPassword(outTrainer.getUsername(), outTrainer.getPassword());
    }

    @GetMapping(value = "/getProfile")
    public Trainer getProfile(@RequestBody String username) {
        if (username == null) {
            logger.warn("Insufficient data, missing username");
            return null;
        }
        System.out.println(username);
        Trainer trainer = null;
        try {
            trainer = trainerService.selectTrainer(username);
        } catch (NoSuchElementException e) {
            trainer = trainerRepository.getTrainerByUsername(username);
        }

        return trainer;
    }

    @PutMapping(value = "/updateProfile")
    public Trainer update(@RequestBody SimpleTrainer newTrainerInfo) {
        if (newTrainerInfo == null || newTrainerInfo.getUsername() == null || newTrainerInfo.getLastName() == null || newTrainerInfo.getFirstName() == null) {
            logger.warn("Insufficient data, missing username, first or last name");
            return null;
        }

        if (trainerRepository.getTrainerByUsername(newTrainerInfo.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return null;
        }
        Trainer t = new Trainer();
        try {

            t = trainerService.selectTrainer(newTrainerInfo.getUsername());
            trainerService.updateTrainer(newTrainerInfo.getUsername(),
                    newTrainerInfo.getFirstName(),
                    newTrainerInfo.getLastName(),
                    (newTrainerInfo.getSpecialization() == null) ? t.getSpecialisation() : newTrainerInfo.getSpecialization()
            );
            trainerService.selectTrainer(newTrainerInfo.getUsername()).setActive(newTrainerInfo.isActive());
            t = trainerService.selectTrainer(newTrainerInfo.getUsername());
            trainerRepository.save(t);
        } catch (Exception e) {
            t = trainerRepository.getTrainerByUsername(newTrainerInfo.getUsername());
            t.setFirstName(newTrainerInfo.getFirstName());
            t.setLastName(newTrainerInfo.getLastName());
            t.setSpecialisation((newTrainerInfo.getSpecialization() == null) ? t.getSpecialisation() : newTrainerInfo.getSpecialization());
            t.setActive(newTrainerInfo.isActive());
            trainerRepository.save(t);
        }


        return t;
    }

    @GetMapping(value = "/getTrainersTrainingsList")
    public List<Training> getTrainersTrainingsList(@RequestBody String trainerUsername) {
        if (trainerUsername == null) {
            logger.warn("Insufficient data, missing username");
            return null;
        }

        if (trainerRepository.getTrainerByUsername(trainerUsername) == null) {
            logger.warn("Trying to update non-existent trainee");
            return null;
        }
        return traineeRepository.getTrainingsByUsernameAndCriteria(trainerUsername, null, null, null, null);
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
