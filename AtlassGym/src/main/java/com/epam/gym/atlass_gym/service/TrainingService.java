package com.epam.gym.atlass_gym.service;

import com.epam.gym.atlass_gym.dao.TrainingDAO;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TrainingService {

    @Autowired
    private TrainingDAO trainingDAO;

    private Logger logger = LoggerFactory.getLogger(TrainingService.class);

    public TrainingService() {
    }

    public void createTraining(Training training) {
        trainingDAO.createTraining(training);
        logger.info("Training " + training.getTrainingName() + " created");
        logger.info(training.getId() + "");
        logger.info(training.getId() + "");
    }

    public void createTraining(String trainingName, Training_type trainingType, LocalDate trainingDate, Long trainingDuration) {
        trainingDAO.createTraining(trainingName, trainingType, trainingDate, trainingDuration);
        logger.info("Training " + trainingName + " created");
    }

    public Training selectTraining(String trainingName) {
        logger.info("Training " + trainingName + " selected");
        return trainingDAO.selectTraining(trainingName);
    }

    public void dropTrainings() {
        trainingDAO.dropTrainings();
    }
}
