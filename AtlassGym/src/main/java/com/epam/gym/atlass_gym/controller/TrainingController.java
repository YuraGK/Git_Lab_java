package com.epam.gym.atlass_gym.controller;


import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gym/training", consumes = {"application/JSON"})
public class TrainingController {

    @Autowired
    private TrainingRepositoryImpl trainingRepository;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    private Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody Training training) {
        if (training == null ||
                training.getTrainerIds() == null ||
                training.getTraineeIds() == null ||
                training.getTrainingName() == null ||
                training.getTrainingDate() == null ||
                training.getTrainingDuration() == null) {
            logger.warn("Insufficient data, missing training name, trainer or trainee, training date, duration");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (traineeRepository.getTraineeByUsername(training.getTraineeIds().getUsername()) == null) {
            logger.warn("Trying to use non-existent trainee");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (trainerRepository.getTrainerByUsername(training.getTrainerIds().getUsername()) == null) {
            logger.warn("Trying to use non-existent trainer");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Trainee trainee = traineeRepository.getTraineeByUsername(training.getTraineeIds().getUsername());
        Trainer trainer = trainerRepository.getTrainerByUsername(training.getTrainerIds().getUsername());

        System.out.println(training.getTrainingName() + " " + training.getTrainingType().getTraining_type());
        Training train = trainingService.createTraining(training.getTrainingName(), trainee, trainer, training.getTrainingDate(), training.getTrainingDuration());
        trainingRepository.save(train);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getTypes")
    public List<Training_type> getProfile() {
        List<Training_type> types = trainingRepository.getTrainingTypes();
        return types;
    }

}
